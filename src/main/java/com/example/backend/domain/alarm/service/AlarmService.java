package com.example.backend.domain.alarm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AlarmService {

    // 사용자별 emitter 저장소 (단일 서버 기준)
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    // TODO: [유저별 알림] userId 기반 개별 전송 sendToUser 등 확장 예정
    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L); // 1시간
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        try {
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    // TODO: 전체 사용자에게 알림 보내기. Redis Pub/Sub 구조 설계만 문서화, 실제 구현은 추후 진행
    public void sendToAll(String message) {
        for (Map.Entry<Long, SseEmitter> entry : emitters.entrySet()) {
            try {
                entry.getValue().send(SseEmitter.event().name("alarm").data(message));
            } catch (IOException e) {
                emitters.remove(entry.getKey());
            }
        }
    }
}