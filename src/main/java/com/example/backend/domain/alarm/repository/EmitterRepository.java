package com.example.backend.domain.alarm.repository;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EmitterRepository {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(Long memberId, SseEmitter emitter) {
        emitters.put(memberId, emitter);
    }

    public SseEmitter get(Long memberId) {
        return emitters.get(memberId);
    }

    public void remove(Long memberId) {
        emitters.remove(memberId);
    }
}
