package com.example.backend.domain.post.dto;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    private String title;
    private String content;
    private String location;
    private Long storeId;
}
