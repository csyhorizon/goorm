package com.example.backend.domain.comment.service;

import com.example.backend.domain.comment.dto.CommentCreateRequest;
import com.example.backend.domain.comment.dto.CommentResponse;
import com.example.backend.domain.comment.dto.CommentUpdateRequest;
import com.example.backend.domain.comment.entity.Comment;
import com.example.backend.domain.comment.repository.CommentRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {
    private final CommentRepository commentRepository;

    public Page<CommentResponse> getComments(Long postId, Pageable pageable) {
        return commentRepository.findAllByPostIdOrderByIdDesc(postId, pageable)
                .map(CommentResponse::from);
    }
}
