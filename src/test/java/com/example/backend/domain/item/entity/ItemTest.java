package com.example.backend.domain.item.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.support.fixture.MemberFixture;
import com.example.backend.support.fixture.StoreFixture;
import org.junit.jupiter.api.Test;

class ItemTest {
    @Test
    void 상품의_가격은_0원보다_낮을_수_없다() {
        Member member = MemberFixture.김회원();
        Store store = StoreFixture.과일가게(member);
        assertThatThrownBy(() -> new Item("사과", "사입니다과", -1, store))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품의_이름은_공백일_수_없다() {
        Member member = MemberFixture.김회원();
        Store store = StoreFixture.과일가게(member);
        assertThatThrownBy(() -> new Item("", "설명", -1, store))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
