package com.example.backend.domain.post.repository;

import com.example.backend.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> , PostRepositoryCustom {
    Page<Post> findAllByStoreIdOrderByCreatedAtDesc(Long storeId, Pageable pageable);

    default Post findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }



}
