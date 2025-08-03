package com.example.backend.domain.event.controller;

import com.example.backend.domain.event.dto.EventResponse;
import com.example.backend.domain.event.service.EventQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventQueryController {
    private final EventQueryService eventQueryService;

    @Operation(summary = "이벤트 상세 조회", description = "가게가 등록했던 이벤트들 상세 조회합니다.")
    @ApiResponse(responseCode = "200", description = "이벤트 상세 조회 성공")
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventDetail(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventQueryService.getEventDetail(eventId));
    }
}
