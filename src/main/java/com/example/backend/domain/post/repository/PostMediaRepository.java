package com.example.backend.domain.post.repository;

import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.entity.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {

    List<PostMedia> findByPostId(Long postId);

    List<PostMedia> findAllByPost(Post post);
}
