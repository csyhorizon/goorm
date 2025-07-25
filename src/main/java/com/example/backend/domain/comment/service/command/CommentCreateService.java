package com.example.backend.domain.comment.service.command;

import com.example.backend.domain.comment.dto.CommentCreateRequest;
import com.example.backend.domain.comment.dto.CommentResponse;
import com.example.backend.domain.comment.entity.Comment;
import com.example.backend.domain.comment.repository.CommentRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.post.entity.Post;
import com.example.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCreateService {

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

    private Comment findParentCommentOrNull(Long parentCommentId) {
        if(parentCommentId == null) return null;
        return commentRepository.findOrThrow(parentCommentId);
    }
}
