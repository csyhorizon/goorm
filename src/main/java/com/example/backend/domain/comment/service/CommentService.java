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
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentResponse createComment(Long memberId, Long postId, CommentCreateRequest createRequest) {
        Member member = memberRepository.findOrThrow(memberId);
        Post post = postRepository.findOrThrow(postId);
        Comment parentComment = findParentCommentOrNull(createRequest.getParentCommentId());

        Comment comment = Comment.of(member, post, parentComment, createRequest.getContent());
        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getComments(Long postId, Pageable pageable) {
        return commentRepository.findAllByPostIdOrderByIdDesc(postId, pageable)
                .map(CommentResponse::from);
    }

    public CommentResponse updateComment(Long commentId, Long memberId, CommentUpdateRequest updateRequest) {
        Comment comment = commentRepository.findOrThrow(commentId);
        Member member = memberRepository.findOrThrow(memberId);
        validateIsOwner(member, comment);
        comment.update(updateRequest.getContent());
        return CommentResponse.from(comment);
    }

    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findOrThrow(commentId);
        Member member = memberRepository.findOrThrow(memberId);
        validateIsOwner(member, comment);
        commentRepository.delete(comment);
    }

    private void validateIsOwner(Member member, Comment comment) {
        if(!Objects.equals(member.getId(), comment.getOwnerId())){
            throw new IllegalArgumentException("this member is not owner of this comment");
        }
    }

    private Comment findParentCommentOrNull(Long parentCommentId) {
        if(parentCommentId == null) return null;
        return commentRepository.findOrThrow(parentCommentId);
    }
}
