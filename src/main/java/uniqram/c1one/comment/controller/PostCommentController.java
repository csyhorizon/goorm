package uniqram.c1one.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.comment.dto.CommentCreateRequest;
import uniqram.c1one.comment.dto.CommentListResponse;
import uniqram.c1one.comment.dto.CommentResponse;
import uniqram.c1one.comment.exception.CommentSuccessCode;
import uniqram.c1one.comment.service.CommentService;
import uniqram.c1one.global.success.SuccessResponse;
import uniqram.c1one.security.adapter.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final CommentService commentService;

    @Operation(summary = "게시글에 댓글 작성", description = "특정 게시글에 새 댓글을 작성합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @PostMapping
    public ResponseEntity<SuccessResponse<CommentResponse>> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest){
        Long userId = userDetails.getUserId();
        CommentResponse response = commentService.createComment(userId, postId, commentCreateRequest);
        return ResponseEntity.status(CommentSuccessCode.COMMENT_CREATED.getStatus())
                .body(SuccessResponse.of(CommentSuccessCode.COMMENT_CREATED, response));
    }

    @Operation(summary = "게시글의 전체 댓글 조회", description = "특정 게시글에 달린 모든 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping
    public ResponseEntity<List<CommentListResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }


}
