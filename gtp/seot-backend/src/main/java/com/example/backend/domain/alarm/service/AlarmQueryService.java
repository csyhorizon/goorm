package com.example.backend.domain.alarm.service;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.alarm.repository.AlarmRepository;
import com.example.backend.domain.alarm.service.command.AlarmCreateService;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmQueryService {

    private static final long SSE_TIMEOUT = 60 * 60 * 1000L; // 1시간
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final AlarmCreateService alarmCreateService;

    private Member findOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
    }

    public SseEmitter subscribe(Long memberId) {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        alarmCreateService.registerEmitter(memberId, emitter);

        emitter.onCompletion(() -> alarmCreateService.removeEmitter(memberId));
        emitter.onTimeout(() -> alarmCreateService.removeEmitter(memberId));
        emitter.onError((e) -> alarmCreateService.removeEmitter(memberId));

        try {
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
        return emitter;
    }

    public long getUnreadCount(Long memberId) {
        Member member = findOrThrow(memberId);
        return alarmRepository.countUnreadByMember(member);
    }

    public List<Alarm> getMyAlarms(Long memberId) {
        Member member = findOrThrow(memberId);
        return alarmRepository.findByMemberAndIsDeletedFalseOrderByCreatedAtDesc(member);
    }
}
