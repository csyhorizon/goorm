package com.example.backend.domain.post.service;

import com.example.backend.domain.global.s3.S3Service;
import com.example.backend.domain.post.dto.PostCreateRequest;
import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.dto.PostUpdateRequest;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.entity.PostMedia;
import com.example.backend.domain.post.repository.PostMediaRepository;
import com.example.backend.domain.post.repository.PostRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.domain.user.entity.Users;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.PostFixture;
import com.example.backend.support.fixture.StoreFixture;
import com.example.backend.support.fixture.UsersFixture;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ServiceTest
public class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    PostMediaRepository postMediaRepository;

    @MockitoBean
    S3Service s3Service;

    @Test
    void createPost() {
        Users user = UsersFixture.김회원();
        userRepository.save(user);

        PostFixture postFixture = PostFixture.이미지_포함_게시글;
        PostCreateRequest postCreateRequest = postFixture.toCreateRequest();
        List<MultipartFile> files = postFixture.getMediaUrls();

        try {
            Mockito.when(s3Service.uploadFile(any(MultipartFile.class)))
                    .thenReturn("http://mock-s3-url/img1.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PostResponse postResponse = postService.createPost(user.getId(), postCreateRequest, files);

        assertEquals(postFixture.getTitle(), postResponse.getTitle());
        assertEquals(postFixture.getContent(), postResponse.getContent());
        assertEquals(postFixture.getLocation(), postResponse.getLocation());
        assertEquals(user.getId(), postResponse.getUserId());
    }

    @Test
    void updatePost() {
        Users user = UsersFixture.김회원();
        Store store = StoreFixture.과일가게(user);
        userRepository.save(user);
        storeRepository.save(store);

        PostFixture postFixture = PostFixture.이미지_포함_게시글;
        PostCreateRequest postCreateRequest = postFixture.toCreateRequest();
        List<MultipartFile> files = postFixture.getMediaUrls();

        try {
            Mockito.when(s3Service.uploadFile(any(MultipartFile.class)))
                    .thenReturn("http://mock-s3-url/img1.png", "http://mock-s3-url/img2.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PostResponse postResponse = postService.createPost(user.getId(), postCreateRequest, files);

        Long postId = postResponse.getId();
        Long storeId = store.getId();

        List<String> keepMediaUrls = List.of("http://mock-s3-url/img1.png");

        MockMultipartFile newImage = new MockMultipartFile(
                "images", "img3.png", "image/png", "c".getBytes()
        );
        List<MultipartFile> newFiles = List.of(newImage);

        try {
            Mockito.when(s3Service.uploadFile(any(MultipartFile.class)))
                    .thenReturn("http://mock-s3-url/img3.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PostUpdateRequest updateRequest = PostUpdateRequest.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .location("수정된 장소")
                .keepMediaUrls(keepMediaUrls)
                .storeId(storeId)
                .build();

        PostResponse updated = postService.updatePost(postId, updateRequest, newFiles);

        assertEquals("수정된 제목", updated.getTitle());
        assertEquals("수정된 내용", updated.getContent());
        assertEquals("수정된 장소", updated.getLocation());
        assertEquals(2, updated.getMediaUrls().size());
        assertTrue(updated.getMediaUrls().contains("http://mock-s3-url/img1.png")); // 기존 이미지 유지
        assertTrue(updated.getMediaUrls().contains("http://mock-s3-url/img3.png")); // 새 이미지 추가
        assertFalse(updated.getMediaUrls().contains("http://mock-s3-url/img2.png")); // img2.png는 삭제
    }

    @Test
    void deletePost() {
        Users user = UsersFixture.김회원();
        userRepository.save(user);
        Store store = StoreFixture.과일가게(user);
        storeRepository.save(store);

        PostFixture postFixture = PostFixture.이미지_포함_게시글;
        PostCreateRequest postCreateRequest = postFixture.toCreateRequest();
        List<MultipartFile> files = postFixture.getMediaUrls();

        try {
            Mockito.when(s3Service.uploadFile(any(MultipartFile.class)))
                    .thenReturn("http://mock-s3-url/img1.png", "http://mock-s3-url/img2.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PostResponse postResponse = postService.createPost(user.getId(), postCreateRequest, files);
        Long postId = postResponse.getId();

        // S3Service Mock: 삭제 호출 기대
        Mockito.doNothing().when(s3Service).deleteFile(anyString());

        // 2. 게시글 삭제
        postService.deletePost(postId);

        // 3. 실제로 DB에서 삭제되었는지 검증
        assertFalse(postRepository.findById(postId).isPresent());

        // 4. 이미지도 PostMedia에서 삭제되었는지 검증 (optional)
        List<PostMedia> medias = postMediaRepository.findByPostId(postId);
        assertTrue(medias.isEmpty());

        // 5. S3Service의 deleteFile이 2회 호출되었는지 검증 (옵션)
        Mockito.verify(s3Service, Mockito.times(2)).deleteFile(anyString());
    }

}
