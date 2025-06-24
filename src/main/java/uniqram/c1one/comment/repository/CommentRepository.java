package uniqram.c1one.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.post.entity.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
    List<Comment> findByParentComment(Comment parentComment);
}
