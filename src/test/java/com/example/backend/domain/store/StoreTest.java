package com.example.backend.domain.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.entity.StoreCategory;
import com.example.backend.domain.store.entity.StoreDuration;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.support.fixture.MemberFixture;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class StoreTest {
    @Test
    void 종료_시간이_시작_시간보다_빠르면_예외() {
        Member users = MemberFixture.김회원();
        assertThatThrownBy(
                () -> new Store("test", "testAddress", "phoneNumber", "description", StoreCategory.FRUIT_SHOP,
                        new StoreDuration(LocalTime.of(12, 0), LocalTime.of(9, 0)), users))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
