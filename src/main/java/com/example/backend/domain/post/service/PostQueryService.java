package com.example.backend.domain.post.service;

import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.entity.PostMedia;
import com.example.backend.domain.post.repository.PostMediaRepository;
import com.example.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostRepository postRepository;
    private final PostMediaRepository postMediaRepository;


    public Page<PostResponse> getPostsByStore(Long storeId, Pageable pageable) {
        Page<Post> page = postRepository.findAllByStoreIdOrderByCreatedAtDesc(storeId, pageable);

        return page.map(post -> {
            List<String> mediaUrls = postMediaRepository.findAllByPost(post).stream()
                    .map(PostMedia::getMediaUrl)
                    .toList();
            return PostResponse.of(post, mediaUrls, post.getStore());
        });
    }

    public PostResponse getPostDetail(Long postId) {
        Post post = postRepository.findOrThrow(postId);
        List<String> mediaUrls = postMediaRepository.findAllByPost(post).stream()
                .map(PostMedia::getMediaUrl)
                .toList();
        return PostResponse.of(post, mediaUrls, post.getStore());
    }

//    public Page<PostResponse> getRecentPosts(Pageable pageable) {
//        Page<Post> posts = postRepository.findAllOrderByCreatedAtDesc(pageable);
//
//        return posts.map(post -> {
//            List<String> mediaUrls = postMediaRepository.findAllByPost(post).stream()
//                    .map(PostMedia::getMediaUrl)
//                    .toList();
//            return PostResponse.of(post, mediaUrls, post.getStore());
//        });
//    }

    public Page<PostResponse> getRecentPosts(Pageable pageable) {
        return postRepository.findAllRecentPosts(pageable);
    }

}
