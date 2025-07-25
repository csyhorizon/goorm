package com.example.backend.domain.alarm.service.command;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.alarm.repository.AlarmRepository;
import com.example.backend.domain.alarm.repository.EmitterRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmCreateService {
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final EmitterRepository emitterRepository;

    public void create(Long memberId, String message) {
        Member member = findOrThrow(memberId);
        SseEmitter emitter = emitterRepository.get(memberId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("alarm").data(message));
            } catch (IOException e) {
                log.warn("Failed to send SSE to memberId {}. Removing emitter.", memberId);
                emitterRepository.remove(memberId);
            }
        }
        Alarm alarm = Alarm.builder()
                .member(member)
                .content(message)
                .build();
        alarmRepository.save(alarm);
    }

    public void registerEmitter(Long memberId, SseEmitter emitter) {
        emitterRepository.save(memberId, emitter);
    }

    public void removeEmitter(Long memberId) {
        emitterRepository.remove(memberId);
    }

    private Member findOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
    }
}
