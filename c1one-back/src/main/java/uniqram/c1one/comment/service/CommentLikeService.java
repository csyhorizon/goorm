package uniqram.c1one.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.comment.dto.CommentLikeResponse;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.comment.entity.CommentLike;
import uniqram.c1one.comment.exception.CommentErrorCode;
import uniqram.c1one.comment.exception.CommentException;
import uniqram.c1one.comment.repository.CommentLikeRepository;
import uniqram.c1one.comment.repository.CommentRepository;
import uniqram.c1one.global.service.LikeCountService;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final LikeCountService likeCountService;

    @Transactional
    public CommentLikeResponse likeComment(Long userId, Long commentId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        Optional<CommentLike> existing = commentLikeRepository.findByUserAndComment(user, comment);

        boolean liked;
        if (existing.isPresent()) {
            commentLikeRepository.delete(existing.get());
            likeCountService.decrementCommentLike(commentId);
            liked = false;
        } else {
            CommentLike commentLike = CommentLike.builder().user(user).comment(comment).build();
            commentLikeRepository.save(commentLike);
            likeCountService.incrementCommentLike(commentId);
            liked = true;
        }

        int likeCount = likeCountService.getCommentLikeCount(commentId);

        return CommentLikeResponse.builder()
                .commentId(commentId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
