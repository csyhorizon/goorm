package com.example.backend.domain.comment.repository;

import com.example.backend.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostIdOrderByIdDesc(Long postId, Pageable pageable);

    default Comment findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    }
}
