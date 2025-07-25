package com.example.backend.domain.comment.service.command;

import com.example.backend.domain.comment.dto.CommentCreateRequest;
import com.example.backend.domain.comment.dto.CommentResponse;
import com.example.backend.domain.comment.dto.CommentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {

    private final CommentCreateService commentCreateService;
    private final CommentDeleteService commentDeleteService;
    private final CommentUpdateService commentUpdateService;

    public CommentResponse createComment(Long memberId, Long postId, CommentCreateRequest commentCreateRequest) {
        return commentCreateService.createComment(memberId, postId, commentCreateRequest);
    }

    public CommentResponse updateComment(Long commentId, Long memberId, CommentUpdateRequest commentUpdateRequest) {
        return commentUpdateService.updateComment(commentId, memberId, commentUpdateRequest);
    }

    public void deleteComment(Long commentId, Long memberId) {
        commentDeleteService.deleteComment(commentId, memberId);
    }
}
