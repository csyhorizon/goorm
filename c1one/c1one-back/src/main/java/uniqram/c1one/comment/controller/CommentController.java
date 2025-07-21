package uniqram.c1one.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.comment.dto.CommentCreateRequest;
import uniqram.c1one.comment.dto.CommentLikeResponse;
import uniqram.c1one.comment.dto.CommentResponse;
import uniqram.c1one.comment.dto.CommentUpdateRequest;
import uniqram.c1one.comment.exception.CommentSuccessCode;
import uniqram.c1one.comment.service.CommentLikeService;
import uniqram.c1one.comment.service.CommentService;
import uniqram.c1one.global.success.SuccessResponse;
import uniqram.c1one.security.adapter.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/comments/{commentId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @Operation(summary = "댓글 수정", description = "특정 댓글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.")
    @ApiResponse(responseCode = "403", description = "수정 또는 삭제 권한이 없습니다.")
    @PatchMapping
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentUpdateRequest updateRequest
    ) {
        Long userId = userDetails.getUserId();
        CommentResponse response = commentService.updateComment(userId, commentId, updateRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.")
    @ApiResponse(responseCode = "403", description = "수정 또는 삭제 권한이 없습니다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 좋아요", description = "특정 댓글에 좋아요를 합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.")
    @PostMapping("/like")
    public ResponseEntity<CommentLikeResponse> likeComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long userId = userDetails.getUserId();
        CommentLikeResponse like = commentLikeService.likeComment(userId, commentId);
        return ResponseEntity.ok(like);
    }
}
