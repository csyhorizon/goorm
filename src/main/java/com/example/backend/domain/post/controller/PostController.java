package com.example.backend.domain.post.controller;

import com.example.backend.domain.post.dto.PostCreateRequest;
import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.dto.PostUpdateRequest;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.service.PostService;
import com.example.backend.domain.security.adapter.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> createPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart("postCreateRequest") PostCreateRequest postCreateRequest,
            @RequestPart("images") List<MultipartFile> images
    ) {
        Long userId = userDetails.getUserId();
        PostResponse response = postService.createPost(userId, postCreateRequest, images);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Page<PostResponse>> getPostsByStore(
            @PathVariable Long storeId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.getPostsByStore(storeId, pageable));
    }

    @GetMapping("/{storeId}/{postId}")
    public ResponseEntity<PostResponse> getDetailPosts(@PathVariable Long storeId,
                                                       @PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }

    @PatchMapping(value = "/{storeId}/{postId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @PathVariable Long storeId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart("postUpdateRequest") PostUpdateRequest postUpdateRequest,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        PostResponse updated = postService.updatePost(postId, userDetails.getUserId(), postUpdateRequest, images);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{storeId}/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @PathVariable Long storeId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(postId, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
