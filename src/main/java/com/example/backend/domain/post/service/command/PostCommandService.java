package com.example.backend.domain.post.service.command;

import com.example.backend.domain.post.dto.PostCreateRequest;
import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.dto.PostUpdateRequest;
import com.example.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommandService {

    private final PostCreateService postCreateService;
    private final PostUpdateService postUpdateService;
    private final PostDeleteService postDeleteService;


    public PostResponse createPost(Long memberId, PostCreateRequest postCreateRequest, List<MultipartFile> imageFiles) {
        return postCreateService.createPost(memberId, postCreateRequest, imageFiles);
    }

    public PostResponse updatePost(Long postId, Long memberId ,PostUpdateRequest postUpdateRequest, List<MultipartFile> imageFiles) {
        return postUpdateService.updatePost(postId, memberId, postUpdateRequest, imageFiles);
    }

    public void deletePost(Long postId, Long memberId) {
        postDeleteService.deletePost(postId, memberId);
    }
}
