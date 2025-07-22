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
@Transactional
public class EventService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final EventRepository eventRepository;

    public EventResponse save(Long memberId, Long storeId, EventCreateRequest eventCreateRequest) {
        Member member = memberRepository.findOrThrow(memberId);
        Store store = storeRepository.findOrThrow(storeId);
        validateIsOwner(member, store);

        Event event = eventRepository.save(eventCreateRequest.toEntity(store));
        return EventResponse.from(event);
    }

    private void validateIsOwner(Member member, Store store) {
        if (!Objects.equals(store.getOwnerId(), member.getId())) {
            throw new IllegalArgumentException("this user is not owner");
        }
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents(Long storeId) {
        Store store = storeRepository.findOrThrow(storeId);
        List<Event> events = eventRepository.findAllByStoreId(store.getId());

        return events.stream()
                .map(EventResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public EventResponse getEventDetail(Long eventId) {
        Event event = findOrThrow(eventId);
        return EventResponse.from(event);
    }

    public void delete(Long eventId) {
        Event event = findOrThrow(eventId);
        eventRepository.delete(event);
    }

    private Event findOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
    }
}
