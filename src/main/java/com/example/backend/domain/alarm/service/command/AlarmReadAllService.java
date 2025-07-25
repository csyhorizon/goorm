package com.example.backend.domain.alarm.service.command;

import com.example.backend.domain.alarm.repository.AlarmRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmReadAllService {
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void readAll(Long memberId) {
        Member member = memberRepository.findOrThrow(memberId);
        alarmRepository.markAllReadByMember(member);
    }
}
