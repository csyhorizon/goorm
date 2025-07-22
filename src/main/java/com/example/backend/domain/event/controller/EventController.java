package com.example.backend.domain.event.controller;

import com.example.backend.domain.event.dto.EventResponse;
import com.example.backend.domain.event.service.EventService;
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
public class EventController {
    private final EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventDetail(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventDetail(eventId));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable Long eventId) {
        eventService.delete(eventId);
        return ResponseEntity.noContent().build();
    }
}
