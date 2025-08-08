package com.example.backend.domain.member.service;

import com.example.backend.domain.member.dto.MemberResponse;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findOrThrow(memberId);
        return MemberResponse.from(member);
    }
}
