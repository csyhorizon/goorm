package com.example.backend.domain.post.controller;

import com.example.backend.domain.post.dto.PostCreateRequest;
import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.dto.PostUpdateRequest;

import com.example.backend.domain.post.service.PostService;
import com.example.backend.domain.security.adapter.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "이미지 포함 게시글 생성", description = "이미지를 포함한 게시글을 작성합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 생성 성공")
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

    @Operation(summary = "게시글 조회", description = "특정 가게별 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 조회 성공")
    @GetMapping("/{storeId}")
    public ResponseEntity<Page<PostResponse>> getPostsByStore(
            @PathVariable Long storeId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.getPostsByStore(storeId, pageable));
    }

    @Operation(summary = "게시글 세부 조회", description = "특정 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 조회 성공")
    @GetMapping("/{storeId}/{postId}")
    public ResponseEntity<PostResponse> getDetailPosts(@PathVariable Long storeId,
                                                       @PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }

    @Operation(summary = "게시글 수정", description = "특정 게시글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 수정 성공")
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

    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "게시글 삭제 성공")
    @DeleteMapping("/{storeId}/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @PathVariable Long storeId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.deletePost(postId, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
