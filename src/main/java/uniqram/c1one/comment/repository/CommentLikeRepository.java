package uniqram.c1one.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.comment.entity.CommentLike;
import uniqram.c1one.user.entity.Users;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    int countByComment(Comment comment);
    boolean existsByUserAndComment(Users user, Comment comment);
    Optional<CommentLike> findByUserAndComment(Users user, Comment comment);
}
