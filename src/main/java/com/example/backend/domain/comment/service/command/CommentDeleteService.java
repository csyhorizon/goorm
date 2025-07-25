package com.example.backend.domain.comment.service.command;

import com.example.backend.domain.comment.entity.Comment;
import com.example.backend.domain.comment.repository.CommentRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentDeleteService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

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
}
