package com.example.backend.domain.comment.controller;

import com.example.backend.domain.comment.dto.CommentResponse;
import com.example.backend.domain.comment.dto.CommentUpdateRequest;
import com.example.backend.domain.comment.service.CommentService;
import com.example.backend.domain.security.adapter.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 수정", description = "특정 댓글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 수정 성공")
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        CommentResponse response = commentService.updateComment(commentId, userDetails.getUserId(), commentUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        commentService.deleteComment(commentId, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
