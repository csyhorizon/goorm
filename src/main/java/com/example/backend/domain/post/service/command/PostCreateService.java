package com.example.backend.domain.post.service.command;

import com.example.backend.domain.global.s3.S3Service;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.post.dto.PostCreateRequest;
import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.entity.PostMedia;
import com.example.backend.domain.post.repository.PostMediaRepository;
import com.example.backend.domain.post.repository.PostRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCreateService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostMediaRepository postMediaRepository;
    private final S3Service s3Service;
    private final StoreRepository storeRepository;

    public PostResponse createPost(Long memberId, PostCreateRequest postCreateRequest, List<MultipartFile> imageFiles) {
        Member member = memberRepository.findOrThrow(memberId);

        Store store = storeRepository.findOrThrow(postCreateRequest.getStoreId());

        List<String> mediaUrls = imageFiles.stream()
                .map(file -> {
                    try {
                        return s3Service.uploadFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("S3 업로드 실패", e);
                    }
                })
                .toList();

        validateMediaUrls(mediaUrls);

        //post 생성
        Post post = Post.of(member, store, postCreateRequest.getLocation(), postCreateRequest.getTitle(), postCreateRequest.getContent());
        postRepository.save(post);

        //정적 팩토리 메서드 이미지 등록(추후 CQRS 패턴 대비)
        List<PostMedia> mediaList = mediaUrls.stream()
                .map(url -> PostMedia.of(post, url))
                .toList();
        postMediaRepository.saveAll(mediaList);

        return PostResponse.of(post, mediaUrls, store);
    }

    private void validateMediaUrls(List<String> mediaUrls) {
        if (mediaUrls == null || mediaUrls.isEmpty()) {
            throw new IllegalArgumentException("MediaUrls cannot be empty");
        }
    }
}
