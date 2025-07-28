package com.example.backend.domain.event.service;

import com.example.backend.domain.event.dto.EventCreateRequest;
import com.example.backend.domain.event.dto.EventResponse;
import com.example.backend.domain.event.entity.Event;
import com.example.backend.domain.event.repository.EventRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventQueryService {
    private final StoreRepository storeRepository;
    private final EventRepository eventRepository;

    public List<EventResponse> getAllEvents(Long storeId) {
        Store store = storeRepository.findOrThrow(storeId);
        List<Event> events = eventRepository.findAllByStoreId(store.getId());

        return events.stream()
                .map(EventResponse::from)
                .toList();
    }

    public EventResponse getEventDetail(Long eventId) {
        Event event = eventRepository.findOrThrow(eventId);
        return EventResponse.from(event);
    }
}
