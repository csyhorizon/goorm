package com.example.backend.domain.post.service;

import com.example.backend.domain.global.s3.S3Service;
import com.example.backend.domain.post.dto.PostCreateRequest;
import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.dto.PostUpdateRequest;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.entity.PostMedia;
import com.example.backend.domain.post.repository.PostMediaRepository;
import com.example.backend.domain.post.repository.PostRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.domain.user.entity.Users;
import com.example.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMediaRepository postMediaRepository;
    private final S3Service s3Service;
    private final StoreRepository storeRepository;

    @Transactional
    public PostResponse createPost(Long userId, PostCreateRequest postCreateRequest, List<MultipartFile> imageFiles) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Store store = storeRepository.findOrThrow(postCreateRequest.getStoreId());

        List<String> mediaUrls = imageFiles.stream()
                .map(file -> {
                    try {
                        return s3Service.uploadFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("S3 업로드 실패", e);
                    }
                })
                .collect(Collectors.toList());

        if (mediaUrls == null || mediaUrls.isEmpty()) {
            throw new IllegalArgumentException("MediaUrls cannot be empty");
        }

        //post 생성
        Post post = Post.of(user, store, postCreateRequest.getLocation(), postCreateRequest.getTitle(), postCreateRequest.getContent());
        postRepository.save(post);

        //정적 팩토리 메서드 이미지 등록(추후 CQRS 패턴 대비)
        List<PostMedia> mediaList = mediaUrls.stream()
                .map(url -> PostMedia.of(post, url))
                .collect(Collectors.toList());
        postMediaRepository.saveAll(mediaList);

        return PostResponse.from(post, mediaUrls, store);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByStore(Long storeId, Pageable pageable) {
        Page<Post> page = postRepository.findAllByStoreIdOrderByCreatedAtDesc(storeId, pageable);

        return page.map(post -> {
            List<String> mediaUrls = postMediaRepository.findAllByPost(post).stream()
                    .map(PostMedia::getMediaUrl)
                    .collect(Collectors.toList());
            return PostResponse.from(post, mediaUrls, post.getStore());
        });
    }

    @Transactional(readOnly = true)
    public PostResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        List<String> mediaUrls = postMediaRepository.findAllByPost(post).stream()
                .map(PostMedia::getMediaUrl)
                .collect(Collectors.toList());
        return PostResponse.from(post, mediaUrls, post.getStore());
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostUpdateRequest postUpdateRequest, List<MultipartFile> imageFiles) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        //기존 이미지 조회
        List<PostMedia> allPosts = postMediaRepository.findAllByPost(post);

        //NPE 방지
        List<String> keepMediaUrls = postUpdateRequest.getKeepMediaUrls() != null
                ? postUpdateRequest.getKeepMediaUrls()
                : List.of();

        List<PostMedia> toDelete = allPosts.stream()
                .filter(media -> !keepMediaUrls.contains(media.getMediaUrl()))
                .toList();

        for (PostMedia media : toDelete) {
            s3Service.deleteFile(media.getMediaUrl());
        }
        postMediaRepository.deleteAll(toDelete);


        if(imageFiles != null && !imageFiles.isEmpty()) {
            List<String> newMediaUrls = imageFiles.stream().map(file -> {
                try {
                    return s3Service.uploadFile(file);
                } catch (IOException e) {
                    throw new RuntimeException("S3 업로드 실패", e);
                }
            }).toList();
            List<PostMedia> newMedia = newMediaUrls.stream().map(url -> PostMedia.of(post, url)).collect(Collectors.toList());
            postMediaRepository.saveAll(newMedia);
        }

        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContent(), postUpdateRequest.getLocation());

        List<String> mediaUrls = postMediaRepository.findAllByPost(post)
                .stream()
                .map(PostMedia::getMediaUrl).toList();
        return PostResponse.from(post, mediaUrls, post.getStore());
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        List<PostMedia> medias = postMediaRepository.findAllByPost(post);

        // S3에서 이미지 삭제
        for (PostMedia media : medias) {
            s3Service.deleteFile(media.getMediaUrl());
        }
        postRepository.delete(post);
    }
}
