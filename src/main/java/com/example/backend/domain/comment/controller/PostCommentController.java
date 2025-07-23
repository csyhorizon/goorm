package com.example.backend.domain.comment.controller;

import com.example.backend.domain.comment.dto.CommentCreateRequest;
import com.example.backend.domain.comment.dto.CommentResponse;
import com.example.backend.domain.comment.service.CommentService;
import com.example.backend.domain.security.adapter.CustomUserDetails;
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

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest){
        CommentResponse comment = commentService.createComment(userDetails.getUserId(), postId, commentCreateRequest);
        return ResponseEntity.ok(comment);
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponse>> getComments(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable){
        Page<CommentResponse> comments = commentService.getComments(postId, pageable);
        return ResponseEntity.ok(comments);
    }
}
