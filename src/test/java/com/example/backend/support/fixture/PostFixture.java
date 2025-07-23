package com.example.backend.support.fixture;

import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.post.dto.PostCreateRequest;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.store.entity.Store;
import lombok.Getter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public enum PostFixture {

    이미지_포함_게시글(
            "테스트 제목",
            "테스트 내용",
            "서울",
            List.of(
                    new MockMultipartFile("images", "img1.png", "image/png", "a".getBytes()),
                    new MockMultipartFile("images", "img2.png", "image/png", "b".getBytes())
            ),
            1L
    );

    private final String title;
    private final String content;
    private final String location;
    private final List<MultipartFile> mediaUrls;
    private final Long storeId;

    PostFixture(String title, String content, String location, List<MultipartFile> mediaUrls, Long storeId) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.mediaUrls = mediaUrls;
        this.storeId = storeId;
    }

    public PostCreateRequest toCreateRequest() {
        return new PostCreateRequest(
                title, content, location, storeId);
    }

    public static Post 게시글(Member member, Store store) {
        return new Post(member, store, 이미지_포함_게시글.location, 이미지_포함_게시글.title, 이미지_포함_게시글.content);
    }

}
