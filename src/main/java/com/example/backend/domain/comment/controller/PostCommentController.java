package com.example.backend.domain.comment.controller;

import com.example.backend.domain.comment.dto.CommentCreateRequest;
import com.example.backend.domain.comment.dto.CommentResponse;
import com.example.backend.domain.comment.service.CommentService;
import com.example.backend.domain.security.adapter.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class PostCommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "특정 게시글에 댓글을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 생성 성공")
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest){
        CommentResponse comment = commentService.createComment(userDetails.getUserId(), postId, commentCreateRequest);
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "댓글 조회", description = "특정 게시글의 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 조회 성공")
    @GetMapping
    public ResponseEntity<Page<CommentResponse>> getComments(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable){
        Page<CommentResponse> comments = commentService.getComments(postId, pageable);
        return ResponseEntity.ok(comments);
    }
}
