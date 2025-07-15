package org.chinoel.goormspring.repository;

import java.util.List;
import org.chinoel.goormspring.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByPostIdAndIsDeletedFalseOrderByCreatedAtAsc(Long postId);
}
