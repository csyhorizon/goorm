package com.example.backend.domain.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.backend.domain.store.dto.StoreCreateRequest;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.entity.StoreCategory;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.service.command.StoreCommandService;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.MemberFixture;
import com.example.backend.support.fixture.StoreFixture;

@ServiceTest
public class StoreCommandServiceTest {
    @Autowired
    StoreCommandService storeCommandService;
    @Autowired
    StoreQueryService storeService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 가게를_저장할_수_있다() {
        Member user = MemberFixture.김회원();
        memberRepository.save(user);
        Store store = StoreFixture.과일가게(user);
        storeRepository.save(store);

        assertThat(storeRepository.findAll()).hasSize(1);
    }

    @Test
    void 가게는_하나만_저장할_수_있다() {
        Member user = MemberFixture.김회원();
        memberRepository.save(user);
        Store store = StoreFixture.과일가게(user);
        storeRepository.save(store);

        StoreCreateRequest storeCreateRequest = new StoreCreateRequest(
                "맛있는김밥집",
                "서울특별시 강남구 테헤란로 123",
                "010-1234-5678",
                "신선한 재료로 만드는 수제 김밥!",
                StoreCategory.FRUIT_SHOP,
                LocalTime.of(9, 0),
                LocalTime.of(21, 0)
        );
        assertThatThrownBy(() -> storeCommandService.save(user.getId(), storeCreateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 권한이_사장이어야만_가게를_저장할_수_있다() {
        Member user = MemberFixture.김회투();
        memberRepository.save(user);

        StoreCreateRequest storeCreateRequest = new StoreCreateRequest(
                "맛있는김밥집",
                "서울특별시 강남구 테헤란로 123",
                "010-1234-5678",
                "신선한 재료로 만드는 수제 김밥!",
                StoreCategory.FRUIT_SHOP,
                LocalTime.of(9, 0),
                LocalTime.of(21, 0)
        );
        assertThatThrownBy(() -> storeCommandService.save(user.getId(), storeCreateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 가게를_삭제할_수_있다() {
        Member user = MemberFixture.김회원();
        memberRepository.save(user);
        Store store = StoreFixture.과일가게(user);
        storeRepository.save(store);

        storeRepository.delete(store);

        assertThat(storeRepository.findAll()).hasSize(0);
    }
}
