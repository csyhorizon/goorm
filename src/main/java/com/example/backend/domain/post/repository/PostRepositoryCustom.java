package com.example.backend.domain.post.repository;

import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostResponse> findAllRecentPosts(Pageable pageable);
}
