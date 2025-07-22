package com.example.backend.domain.event.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.domain.event.entity.Event;
import com.example.backend.domain.event.repository.EventRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.EventFixture;
import com.example.backend.support.fixture.MemberFixture;
import com.example.backend.support.fixture.StoreFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class EventServiceTest {
    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StoreRepository storeRepository;

    @Test
    void 이벤트를_저장할_수_있다() {
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        Store store = StoreFixture.과일가게(member);
        storeRepository.save(store);
        Event event = EventFixture.천원_할인(store);
        eventRepository.save(event);

        assertThat(eventRepository.findAll()).hasSize(1);
    }

    @Test
    void 이벤트를_삭제할_수_있다() {
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        Store store = StoreFixture.과일가게(member);
        storeRepository.save(store);
        Event event = EventFixture.천원_할인(store);
        eventRepository.save(event);

        eventService.delete(event.getId());
        assertThat(eventRepository.findAll()).hasSize(0);
    }
}
