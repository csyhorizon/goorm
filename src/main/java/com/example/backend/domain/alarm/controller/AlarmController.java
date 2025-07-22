package com.example.backend.domain.alarm.controller;

import com.example.backend.domain.alarm.entity.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.example.backend.domain.alarm.service.AlarmService;
import com.example.backend.domain.alarm.dto.AlarmResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class AlarmController {

    private final AlarmService alarmService;

    // TODO: [유저별 알림] userId, 상점 정보 등 연동 시, subscribe 파라미터 확장 예정
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam Long memberId) {
        return alarmService.subscribe(memberId);
    }

    // 안 읽은 알림 개수 조회
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam Long memberId) {
        return ResponseEntity.ok(alarmService.getUnreadCount(memberId));
    }

    // 전체 알림 읽음 처리
    @PostMapping("/read-all")
    public ResponseEntity<Void> readAll(@RequestParam Long memberId) {
        alarmService.readAll(memberId);
        return ResponseEntity.noContent().build();
    }

    // 알림 개별 삭제
    @DeleteMapping("/{alarmId}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long alarmId) {
        alarmService.deleteAlarm(alarmId);
        return ResponseEntity.noContent().build();
    }

    // 내 알림 리스트 조회
    @GetMapping
    public ResponseEntity<List<AlarmResponse>> getMyAlarms(@RequestParam Long memberId) {
        List<Alarm> alarms = alarmService.getMyAlarms(memberId);
        List<AlarmResponse> response = alarms.stream()
                .map(AlarmResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
