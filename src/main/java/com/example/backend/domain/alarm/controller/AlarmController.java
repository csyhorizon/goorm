package com.example.backend.domain.alarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.example.backend.domain.alarm.service.AlarmService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class AlarmController {

    private final AlarmService alarmService;

    // TODO: [유저별 알림] userId, 상점 정보 등 연동 시, subscribe 파라미터 확장 예정
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam Long userId) {
        return alarmService.subscribe(userId);
    }

    // TODO: 테스트용 전체 알림 전송 엔드포인트. 추후 제거
    @PostMapping("/test")
    public void testSend(@RequestParam String message) {
        alarmService.sendToAll(message);
    }
}