package com.example.backend.domain.alarm.controller;

import com.example.backend.domain.alarm.dto.AlarmResponse;
import com.example.backend.domain.alarm.service.AlarmQueryService;
import com.example.backend.domain.auth.jwt.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class AlarmQueryController {

    private final AlarmQueryService alarmQueryService;

    @Operation(summary = "SSE 구독 요청", description = "로그인한 회원의 실시간 알림 수신을 위한 SSE 연결을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "SSE 연결 성공")
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails user) {
        return alarmQueryService.subscribe(user.getUserId());
    }

    @Operation(summary = "안 읽은 알림 개수 조회", description = "로그인한 회원의 읽지 않은 알림 개수를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "알림 개수 조회 성공")
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(alarmQueryService.getUnreadCount(user.getUserId()));
    }

    @Operation(summary = "내 알림 목록 조회", description = "로그인한 회원의 알림 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "알림 목록 조회 성공")
    @GetMapping
    public ResponseEntity<List<AlarmResponse>> getMyAlarms(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(AlarmResponse.from(alarmQueryService.getMyAlarms(user.getUserId())));
    }
}
