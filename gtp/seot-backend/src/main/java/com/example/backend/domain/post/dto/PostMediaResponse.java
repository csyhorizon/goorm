package com.example.backend.domain.post.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMediaResponse {
    private Long id;
    private String mediaUrl;
    private Integer sequence;

    public PostMediaResponse(Long id, String mediaUrl, Integer sequence) {
        this.id = id;
        this.mediaUrl = mediaUrl;
        this.sequence = sequence;
    }
}
