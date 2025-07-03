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
import uniqram.c1one.comment.repository.CommentLikeRepository;
import uniqram.c1one.comment.repository.CommentRepository;
import uniqram.c1one.global.service.LikeCountService;
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
    private final LikeCountService likeCountService;

    public CommentResponse createComment(Long userId, Long postId, CommentCreateRequest createRequest) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.POST_NOT_FOUND));

        Comment parentComment = null;
        if (createRequest.getParentCommentId() != null) {
            parentComment = commentRepository.findById(createRequest.getParentCommentId())
                    .orElseThrow(() -> new CommentException(CommentErrorCode.PARENT_COMMENT_NOT_FOUND));
        }

        Comment comment = Comment.builder()
                .user(users)
                .post(post)
                .parentComment(parentComment)
                .content(createRequest.getContent())
                .build();
        commentRepository.save(comment);

        return CommentResponse.builder()
                .commentId(comment.getId())
                .userId(users.getId())
                .userName(users.getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .parentCommentId(
                        parentComment != null ? parentComment.getId() : null
                )
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findByPost(post);


        return comments.stream()
                .map(comment -> { int likeCount = likeCountService.getCommentLikeCount(comment.getId());
                        return CommentResponse.builder()
                        .commentId(comment.getId())
                        .userName(comment.getUser().getUsername())
                        .content(comment.getContent())
                        .likeCount(likeCount)
                        .createdAt(comment.getCreatedAt())
                        .parentCommentId(
                                comment.getParentComment() != null ? comment.getParentComment().getId() : null
                        )
                        .build();
                }).collect(Collectors.toList());
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
