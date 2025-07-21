package com.example.backend.domain.item.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.backend.domain.item.dto.ItemCreateRequest;
import com.example.backend.domain.item.entity.Item;
import com.example.backend.domain.item.repository.ItemRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.ItemFixture;
import com.example.backend.support.fixture.MemberFixture;
import com.example.backend.support.fixture.StoreFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class ItemServiceTest {
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @Test
    void 상품을_저장할_수_있다() {
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        Store store = StoreFixture.과일가게(member);
        storeRepository.save(store);
        Item item = ItemFixture.사과(store);
        itemRepository.save(item);

        assertThat(itemRepository.findAll()).hasSize(1);
    }

    @Test
    void 가게주인이_아닐_경우_상품_등록할_수_없다() {
        Member owner = MemberFixture.김회원();
        memberRepository.save(owner);
        Member anotherMember = MemberFixture.김회투();
        memberRepository.save(anotherMember);

        Store store = StoreFixture.과일가게(owner);
        storeRepository.save(store);
        ItemCreateRequest request = new ItemCreateRequest("사과", 1500);
        assertThatThrownBy(() -> itemService.save(anotherMember.getId(), store.getId(), request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
