package com.example.backend.domain.alarm.controller;

import com.example.backend.domain.alarm.service.command.AlarmCommandService;
import com.example.backend.domain.auth.jwt.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class AlarmCommandController {

    private final AlarmCommandService alarmCommandService;

    @Operation(summary = "전체 알림 읽음 처리", description = "현재 로그인한 회원의 모든 알림을 읽음 처리합니다.")
    @ApiResponse(responseCode = "204", description = "모든 알림을 성공적으로 읽음 처리함")
    @PostMapping("/read-all")
    public ResponseEntity<Void> readAll(@AuthenticationPrincipal CustomUserDetails user) {
        alarmCommandService.readAll(user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "알림 개별 삭제", description = "알림 ID를 기준으로 현재 사용자의 알림을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "알림 삭제 성공")
    @DeleteMapping("/{alarmId}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long alarmId) {
        alarmCommandService.deleteAlarm(alarmId);
        return ResponseEntity.noContent().build();
    }
}