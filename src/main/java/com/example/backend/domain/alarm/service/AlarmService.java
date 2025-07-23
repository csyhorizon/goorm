package com.example.backend.domain.alarm.service;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.alarm.repository.AlarmRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private static final long SSE_TIMEOUT = 60 * 60 * 1000L; // 1시간
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    private Member findOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
    }

    // SSE 구독(단일 디바이스)
    public SseEmitter subscribe(Long memberId) {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        emitters.put(memberId, emitter);

        emitter.onCompletion(() -> emitters.remove(memberId));
        emitter.onTimeout(() -> emitters.remove(memberId));
        emitter.onError((e) -> emitters.remove(memberId));

        try {
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
        return emitter;
    }

    // 개별 사용자에게 알림 보내기
    public void sendToUser(Long memberId, String message) {
        Member member = findOrThrow(memberId);
        SseEmitter emitter = emitters.get(memberId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("alarm").data(message));
            } catch (IOException e) {
                emitters.remove(memberId);
            }
        }
        // 알림 이력 저장(DB)
        Alarm alarm = Alarm.builder()
                .member(member)
                .content(message)
                .build();
        alarmRepository.save(alarm);
    }

    // 안 읽은 알림 개수 반환
    public long getUnreadCount(Long memberId) {
        Member member = findOrThrow(memberId);
        return alarmRepository.countUnreadByMember(member);
    }

    // 전체 알림 읽음 처리
    @Transactional
    public void readAll(Long memberId) {
        Member member = findOrThrow(memberId);
        alarmRepository.markAllReadByMember(member);
    }

    // 알림 개별 삭제
    @Transactional
    public void deleteAlarm(Long alarmId) {
        alarmRepository.softDeleteById(alarmId);
    }

    // 내 알림 리스트 조회
    public List<Alarm> getMyAlarms(Long memberId) {
        Member member = findOrThrow(memberId);
        return alarmRepository.findByMemberAndIsDeletedFalseOrderByCreatedAtDesc(member);
    }
}
