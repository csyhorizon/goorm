package com.example.backend.domain.event.controller;

import com.example.backend.domain.event.dto.EventResponse;
import com.example.backend.domain.event.service.EventQueryService;
import com.example.backend.domain.event.service.command.EventCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventCommandController {
    private final EventCommandService eventCommandService;

    @Operation(summary = "이벤트 삭제", description = "가게가 등록했던 이벤트를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "이벤트 삭제 성공")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable Long eventId) {
        eventCommandService.delete(eventId);
        return ResponseEntity.noContent().build();
    }
}
