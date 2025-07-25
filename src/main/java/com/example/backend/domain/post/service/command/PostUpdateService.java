package com.example.backend.domain.post.service.command;

import com.example.backend.domain.global.s3.S3Service;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.post.dto.PostResponse;
import com.example.backend.domain.post.dto.PostUpdateRequest;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.entity.PostMedia;
import com.example.backend.domain.post.repository.PostMediaRepository;
import com.example.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PostUpdateService {

    private final PostRepository postRepository;
    private final PostMediaRepository postMediaRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    public PostResponse updatePost(Long postId, Long memberId, PostUpdateRequest postUpdateRequest, List<MultipartFile> imageFiles) {
        Post post = postRepository.findOrThrow(postId);
        Member member = memberRepository.findOrThrow(memberId);
        validateIsOwner(member, post);

        //기존 이미지 조회
        List<PostMedia> allPosts = postMediaRepository.findAllByPost(post);

        //NPE 방지
        List<String> keepMediaUrls = postUpdateRequest.getKeepMediaUrls() != null
                ? postUpdateRequest.getKeepMediaUrls()
                : List.of();

        List<PostMedia> toDelete = allPosts.stream()
                .filter(media -> !keepMediaUrls.contains(media.getMediaUrl()))
                .toList();

        for (PostMedia media : toDelete) {
            s3Service.deleteFile(media.getMediaUrl());
        }
        postMediaRepository.deleteAll(toDelete);


        if(imageFiles != null && !imageFiles.isEmpty()) {
            List<String> newMediaUrls = imageFiles.stream().map(file -> {
                try {
                    return s3Service.uploadFile(file);
                } catch (IOException e) {
                    throw new RuntimeException("S3 업로드 실패", e);
                }
            }).toList();
            List<PostMedia> newMedia = newMediaUrls.stream().map(url -> PostMedia.of(post, url)).toList();
            postMediaRepository.saveAll(newMedia);
        }

        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContent(), postUpdateRequest.getLocation());

        List<String> mediaUrls = postMediaRepository.findAllByPost(post)
                .stream()
                .map(PostMedia::getMediaUrl).toList();
        return PostResponse.of(post, mediaUrls, post.getStore());
    }

    private void validateIsOwner(Member member, Post post) {
        if(!Objects.equals(member.getId(), post.getOwnerId())){
            throw new IllegalArgumentException("this member is not owner of this post");
        }
    }
}
