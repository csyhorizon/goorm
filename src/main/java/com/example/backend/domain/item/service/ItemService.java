package com.example.backend.domain.item.service;

import com.example.backend.domain.item.dto.ItemCreateRequest;
import com.example.backend.domain.item.dto.ItemResponse;
import com.example.backend.domain.item.entity.Item;
import com.example.backend.domain.item.repository.ItemRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;

    public ItemResponse save(Long memberId, Long storeId, ItemCreateRequest itemCreateRequest) {
        Member member = memberRepository.findOrThrow(memberId);
        Store store = storeRepository.findOrThrow(storeId);
        validateIsOwner(member, store);

        Item item = itemRepository.save(itemCreateRequest.toEntity(store));
        return ItemResponse.from(item);
    }

    private void validateIsOwner(Member member, Store store) {
        if (!Objects.equals(member.getId(), store.getOwnerId())) {
            throw new IllegalArgumentException("this member is not Owner to this store");
        }
    }
}
