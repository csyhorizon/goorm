package uniqram.c1one.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
