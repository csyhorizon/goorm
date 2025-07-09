package uniqram.c1one.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniqram.c1one.comment.dto.CommentListResponse;
import uniqram.c1one.comment.dto.CommentResponse;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.post.entity.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    List<Comment> findByPost(Post post);
    List<Comment> findByParentComment(Comment parentComment);

    @Query("SELECT new uniqram.c1one.comment.dto.CommentResponse( " +
            "c.id, " +
            "c.user.id, " +
            "c.user.username, " +
            "c.content, " +
            "0, " +
            "c.createdAt, " +
            "c.modifiedAt, " +
            "c.parentComment.id, " +
            "c.post.id) " +
            "FROM Comment c " +
            "WHERE c.post.id IN :postIds " +
            "ORDER BY c.post.id, c.createdAt DESC")
    List<CommentResponse> findCommentsByPostIds(@Param("postIds") List<Long> postIds);

//    @Query("SELECT new uniqram.c1one.comment.dto.CommentResponse( " +
//            "c.id, " +
//            "c.user.id, " +
//            "c.user.username, " +
//            "c.content, " +
//            "0, " +
//            "c.createdAt, " +
//            "c.modifiedAt, " +
//            "c.parentComment.id, " +
//            "c.post.id) " +
//            "FROM Comment c " +
//            "WHERE c.post.id = :postId " +
//            "ORDER BY c.createdAt ASC")
//    List<CommentResponse> findCommentsByPostId(@Param("postId") Long postId);

}
