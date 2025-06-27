package uniqram.c1one.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.comment.dto.CommentCreateRequest;
import uniqram.c1one.comment.dto.CommentLikeResponse;
import uniqram.c1one.comment.dto.CommentResponse;
import uniqram.c1one.comment.dto.CommentUpdateRequest;
import uniqram.c1one.comment.exception.CommentSuccessCode;
import uniqram.c1one.comment.service.CommentLikeService;
import uniqram.c1one.comment.service.CommentService;
import uniqram.c1one.global.success.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CommentResponse>> createComment(
            @RequestBody CommentCreateRequest commentCreateRequest){
        Long userId = commentCreateRequest.getUserId();
        CommentResponse response = commentService.createComment(userId, commentCreateRequest);
        return ResponseEntity.status(CommentSuccessCode.COMMENT_CREATED.getStatus())
                .body(SuccessResponse.of(CommentSuccessCode.COMMENT_CREATED, response));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestBody CommentUpdateRequest updateRequest
    ) {
        CommentResponse response = commentService.updateComment(userId, commentId, updateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<CommentLikeResponse> likeComment(
            @PathVariable Long commentId,
            @RequestParam Long userId
    ){
        CommentLikeResponse like = commentLikeService.likeComment(userId, commentId);
        return ResponseEntity.ok(like);
    }
}
