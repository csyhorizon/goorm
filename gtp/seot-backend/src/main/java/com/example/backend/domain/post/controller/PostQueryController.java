package com.example.backend.domain.post.controller;

import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.service.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostQueryController {

    private final PostQueryService postQueryService;

    @Operation(summary = "게시글 조회", description = "특정 가게별 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 조회 성공")
    @GetMapping("/{storeId}")
    public ResponseEntity<Page<PostResponse>> getPostsByStore(
            @PathVariable Long storeId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postQueryService.getPostsByStore(storeId, pageable));
    }

    @Operation(summary = "게시글 세부 조회", description = "특정 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 조회 성공")
    @GetMapping("/{storeId}/{postId}")
    public ResponseEntity<PostResponse> getDetailPosts(@PathVariable Long storeId,
                                                       @PathVariable Long postId) {
        return ResponseEntity.ok(postQueryService.getPostDetail(postId));
    }

    @Operation(summary = "전체 게시글 조회", description = "전체 게시글을 최신순으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 조회 성공")
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getRecentPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postQueryService.getRecentPosts(pageable));
    }
}
