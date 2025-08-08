package com.example.backend.domain.post.service.command;

import com.example.backend.domain.global.s3.S3Service;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.entity.PostMedia;
import com.example.backend.domain.post.repository.PostMediaRepository;
import com.example.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PostDeleteService {

    private final PostRepository postRepository;
    private final PostMediaRepository postMediaRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    public void deletePost(Long postId, Long memberId) {
        Post post = postRepository.findOrThrow(postId);
        Member member = memberRepository.findOrThrow(memberId);
        validateIsOwner(member, post);

        List<PostMedia> medias = postMediaRepository.findAllByPost(post);

        // S3에서 이미지 삭제
        for (PostMedia media : medias) {
            s3Service.deleteFile(media.getMediaUrl());
        }
        postRepository.delete(post);
    }

    private void validateIsOwner(Member member, Post post) {
        if(!Objects.equals(member.getId(), post.getOwnerId())){
            throw new IllegalArgumentException("this member is not owner of this post");
        }
    }
}
