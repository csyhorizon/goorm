package com.example.backend.domain.post.dto;

import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String location;
    private Long userId;
    private String username;
    private List<String> mediaUrls;
    private Long storeId;
    private String storeName;

    public static PostResponse from(Post post, List<String> mediaUrl, Store store) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .location(post.getLocation())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .mediaUrls(mediaUrl)
                .storeId(store.getId())
                .storeName(store.getName())
                .build();
    }
}
