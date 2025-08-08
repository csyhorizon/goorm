package com.example.backend.domain.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequest {
    private String title;
    private String content;
    private String location;
    //유지할 이미지의 S3 URL 리스트
    private List<String> keepMediaUrls;
    private Long storeId;

    public PostUpdateRequest(String title, String content, String location, List<String> keepMediaUrls, Long storeId) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.keepMediaUrls = keepMediaUrls;
        this.storeId = storeId;
    }
}
