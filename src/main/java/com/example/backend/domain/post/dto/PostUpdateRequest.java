package com.example.backend.domain.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequest {
    private String title;
    private String content;
    private String location;
    //유지할 이미지의 S3 URL 리스트
    private List<String> keepMediaUrls;
    private Long storeId;
}
