package com.example.backend.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

    @NotBlank
    private String content;

    public CommentUpdateRequest(String content) {
        this.content = content;
    }
}
