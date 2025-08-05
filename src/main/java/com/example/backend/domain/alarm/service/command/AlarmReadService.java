package com.example.backend.domain.alarm.service.command;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.alarm.repository.AlarmRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmReadService {

    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void read(Long memberId, Long alarmId) {
        Member member = memberRepository.findOrThrow(memberId);
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("알림을 찾을 수 없습니다."));

        if (!alarm.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 알림에 접근 권한이 없습니다.");
        }

        if (!alarm.getIsRead()) {
            alarm.markAsRead();
        }
    }
}
