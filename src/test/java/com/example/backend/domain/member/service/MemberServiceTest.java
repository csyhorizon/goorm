package com.example.backend.domain.member.service;

import com.example.backend.domain.member.dto.MemberResponse;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.MemberFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원_찾기(){
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        MemberResponse foundMember = memberService.getMember(member.getId());
        Assertions.assertThat(member.getId()).isEqualTo(foundMember.id());
    }
}
