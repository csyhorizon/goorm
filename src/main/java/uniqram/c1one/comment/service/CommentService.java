package uniqram.c1one.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.comment.dto.CommentCreateRequest;
import uniqram.c1one.comment.dto.CommentResponse;
import uniqram.c1one.comment.dto.CommentUpdateRequest;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.comment.exception.CommentErrorCode;
import uniqram.c1one.comment.exception.CommentException;
import uniqram.c1one.comment.repository.CommentRepository;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponse createComment(Long userId, CommentCreateRequest createRequest) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Post post = postRepository.findById(createRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Comment.CommentBuilder commentBuilder = Comment.builder()
                .user(users)
                .post(post)
                .content(createRequest.getContent());

        if (createRequest.getParentCommentId() != null) {
            Comment parent = commentRepository.findById(createRequest.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
            commentBuilder.parentComment(parent);
        }

        Comment comment = commentRepository.save(commentBuilder.build());

        return CommentResponse.builder()
                .commentId(comment.getId())
                .userId(users.getId())
                .userName(users.getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .parentCommentId(
                        comment.getParentComment() != null ? comment.getParentComment().getId() : null
                )
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return commentRepository.findByPost(post).stream()
                .map(comment -> CommentResponse.builder()
                        .commentId(comment.getId())
                        .userName(comment.getUser().getUsername())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .parentCommentId(
                                comment.getParentComment() != null ? comment.getParentComment().getId() : null
                        )
                        .build()
                ).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse updateComment(Long userId, Long commentId, CommentUpdateRequest updateRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)){
            throw new CommentException(CommentErrorCode.NO_AUTHORITY);
        }

        comment.update(updateRequest.getContent());
        commentRepository.save(comment);

        return CommentResponse.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .userName(comment.getUser().getUsername())
                .content(comment.getContent())
                .modifiedAt(comment.getModifiedAt())
                .parentCommentId(
                        comment.getParentComment() != null ? comment.getParentComment().getId() : null
                )
                .build();
    }

    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if(!comment.getUser().getId().equals(userId)){
            throw new CommentException(CommentErrorCode.NO_AUTHORITY);
        }

        commentRepository.delete(comment);
    }




}
