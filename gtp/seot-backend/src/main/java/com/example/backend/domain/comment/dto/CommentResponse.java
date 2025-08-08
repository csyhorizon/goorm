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
        Long parentCommentId = null;
        if (comment.getParentComment() != null) {
            parentCommentId = comment.getParentComment().getId();
        }
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getId(),
                comment.getMember().getUsername(),
                comment.getPost().getId(),
                parentCommentId,
                comment.getCreatedAt()
        );
    }
}
