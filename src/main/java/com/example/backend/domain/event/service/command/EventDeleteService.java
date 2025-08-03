package com.example.backend.domain.event.service.command;

import com.example.backend.domain.event.entity.Event;
import com.example.backend.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventDeleteService {
    private final EventRepository eventRepository;

    public void delete(Long eventId) {
        Event event = eventRepository.findOrThrow(eventId);
        eventRepository.delete(event);
    }
}
