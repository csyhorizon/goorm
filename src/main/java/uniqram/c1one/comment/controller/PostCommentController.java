package uniqram.c1one.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.comment.dto.CommentCreateRequest;
import uniqram.c1one.comment.dto.CommentResponse;
import uniqram.c1one.comment.exception.CommentSuccessCode;
import uniqram.c1one.comment.service.CommentService;
import uniqram.c1one.global.success.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CommentResponse>> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest){
        Long userId = commentCreateRequest.getUserId();
        CommentResponse response = commentService.createComment(userId, postId,commentCreateRequest);
        return ResponseEntity.status(CommentSuccessCode.COMMENT_CREATED.getStatus())
                .body(SuccessResponse.of(CommentSuccessCode.COMMENT_CREATED, response));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

}
