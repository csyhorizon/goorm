package com.example.backend.domain.alarm.service.command;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.alarm.entity.AlarmType;
import com.example.backend.domain.alarm.repository.AlarmRepository;
import com.example.backend.domain.alarm.repository.EmitterRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AlarmCreateService {
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final EmitterRepository emitterRepository;

    private final RedisTemplate<String, String> redisTemplate;

    public void create(Long memberId, String message) {
        create(memberId, message, AlarmType.SYSTEM, null); // 기본값 SYSTEM + targetUrl 없음
    }

    public void create(Long memberId, String message, AlarmType type) {
        create(memberId, message, type, null); // targetUrl 없음
    }

    public void create(Long memberId, String message, AlarmType type, String targetUrl) {
        Member member = findOrThrow(memberId);

        // Redis 중복 전송 체크
        String dedupKey = "alarm-dedup:" + memberId + ":" + message;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(dedupKey))) {
            log.info("중복 알림 전송 방지 - 이미 전송됨: {}", dedupKey);
            return;
        }

        // SSE 전송
        SseEmitter emitter = emitterRepository.get(memberId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("alarm").data(message));
            } catch (IOException e) {
                log.warn("SSE 전송 실패, emitter 제거됨. memberId: {}", memberId);
                emitterRepository.remove(memberId);
            }
        }

        // 알림 저장
        Alarm alarm = Alarm.builder()
                .member(member)
                .content(message)
                .type(type)
                .targetUrl(targetUrl)
                .build();
        alarmRepository.save(alarm);

        // Redis에 전송 마킹 (중복 방지용, 10분 유지)
        redisTemplate.opsForValue().set(dedupKey, "sent", Duration.ofMinutes(10));
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
