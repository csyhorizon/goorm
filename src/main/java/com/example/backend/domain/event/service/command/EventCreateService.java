package com.example.backend.domain.event.service.command;

import com.example.backend.domain.alarm.entity.AlarmType;
import com.example.backend.domain.alarm.service.command.AlarmCreateService;
import com.example.backend.domain.event.dto.EventCreateRequest;
import com.example.backend.domain.event.dto.EventResponse;
import com.example.backend.domain.event.entity.Event;
import com.example.backend.domain.event.repository.EventRepository;
import com.example.backend.domain.like.entity.StoreLike;
import com.example.backend.domain.like.repository.StoreLikeRepository;
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
public class EventCreateService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final EventRepository eventRepository;
    private final AlarmCreateService alarmCreateService;
    private final StoreLikeRepository storeLikeRepository;

    public EventResponse save(Long memberId, Long storeId, EventCreateRequest eventCreateRequest) {
        Member member = memberRepository.findOrThrow(memberId);
        Store store = storeRepository.findOrThrow(storeId);
        validateIsOwner(member, store);

        Event event = eventRepository.save(eventCreateRequest.toEntity(store));
        List<StoreLike> likes = storeLikeRepository.findAllByStore(store);
        for (StoreLike like : likes) {
            alarmCreateService.create(
                    like.getMember().getId(),
                    store.getName() + "에 새로운 이벤트가 등록되었습니다!",
                    AlarmType.NEW_EVENT,
                    "/store/" + store.getId() + "/event/" + event.getId()
            );
        }
        return EventResponse.from(event);
    }

    private void validateIsOwner(Member member, Store store) {
        if (!Objects.equals(store.getOwnerId(), member.getId())) {
            throw new IllegalArgumentException("this user is not owner");
        }
    }
}
