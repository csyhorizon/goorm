package com.example.backend.domain.comment.dto;

import com.example.backend.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        Long memberId,
        String username,
        Long postId,
        Long parentCommentId,
        LocalDateTime createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getId(),
                comment.getMember().getUsername(),
                comment.getPost().getId(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null,
                comment.getCreatedAt()
        );
    }
}
