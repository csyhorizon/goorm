package com.example.backend.domain.store.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.domain.member.entity.member;
import com.example.backend.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.MemberFixture;
import com.example.backend.support.fixture.StoreFixture;

@ServiceTest
public class StoreServiceTest {
    @Autowired
    StoreService storeService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 가게를_저장할_수_있다() {
        member user = MemberFixture.김회원();
        memberRepository.save(user);
        Store store = StoreFixture.과일가게(user);
        storeRepository.save(store);

        assertThat(storeRepository.findAll()).hasSize(1);
    }

    @Test
    void 가게를_삭제할_수_있다() {
        member user = MemberFixture.김회원();
        memberRepository.save(user);
        Store store = StoreFixture.과일가게(user);
        storeRepository.save(store);

        storeRepository.delete(store);

        assertThat(storeRepository.findAll()).hasSize(0);
    }
}
