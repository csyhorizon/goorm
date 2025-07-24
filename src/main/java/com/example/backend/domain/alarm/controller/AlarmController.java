package com.example.backend.domain.alarm.controller;

import com.example.backend.domain.alarm.entity.Alarm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "SSE 구독 요청", description = "회원 ID를 기반으로 실시간 알림을 수신하기 위한 SSE 연결을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "SSE 연결 성공")
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam Long memberId) {
        return alarmService.subscribe(memberId);
    }

    @Operation(summary = "안 읽은 알림 개수 조회", description = "특정 회원의 읽지 않은 알림 개수를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 알림 개수를 조회함")
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam Long memberId) {
        return ResponseEntity.ok(alarmService.getUnreadCount(memberId));
    }

    @Operation(summary = "전체 알림 읽음 처리", description = "특정 회원의 모든 알림을 읽음 상태로 처리합니다.")
    @ApiResponse(responseCode = "204", description = "모든 알림을 성공적으로 읽음 처리함")
    @PostMapping("/read-all")
    public ResponseEntity<Void> readAll(@RequestParam Long memberId) {
        alarmService.readAll(memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "알림 개별 삭제", description = "알림 ID를 기준으로 특정 알림을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "알림 삭제 성공")
    @DeleteMapping("/{alarmId}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long alarmId) {
        alarmService.deleteAlarm(alarmId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "내 알림 목록 조회", description = "회원 ID를 기반으로 해당 사용자의 알림 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "알림 목록 조회 성공")
    @GetMapping
    public ResponseEntity<List<AlarmResponse>> getMyAlarms(@RequestParam Long memberId) {
        List<Alarm> alarms = alarmService.getMyAlarms(memberId);
        List<AlarmResponse> response = alarms.stream()
                .map(AlarmResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
