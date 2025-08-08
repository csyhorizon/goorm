package com.example.backend.domain.event.service.command;

import com.example.backend.domain.event.dto.EventCreateRequest;
import com.example.backend.domain.event.dto.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventCommandService {
    private final EventCreateService eventCreateService;
    private final EventDeleteService eventDeleteService;

    public EventResponse save(Long memberId, Long storeId, EventCreateRequest eventCreateRequest) {
        return eventCreateService.save(memberId, storeId, eventCreateRequest);
    }

    public void delete(Long eventId) {
        eventDeleteService.delete(eventId);
    }
}
