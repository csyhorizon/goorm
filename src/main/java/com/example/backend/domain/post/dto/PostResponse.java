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
    private Long memberId;
    private String memberName;
    private List<String> mediaUrls;
    private Long storeId;
    private String storeName;

    public static PostResponse of(Post post, List<String> mediaUrl, Store store) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .location(post.getLocation())
                .memberId(post.getMember().getId())
                .memberName(post.getMember().getUsername())
                .mediaUrls(mediaUrl)
                .storeId(store.getId())
                .storeName(store.getName())
                .build();
    }
}
