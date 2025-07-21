package com.example.backend.domain.post.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMediaResponse {
    private Long id;
    private String mediaUrl;
    private Integer sequence;
}
