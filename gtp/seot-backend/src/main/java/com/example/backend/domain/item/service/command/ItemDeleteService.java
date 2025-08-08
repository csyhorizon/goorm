package com.example.backend.domain.item.service.command;

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
public class ItemDeleteService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;

    public void deleteItem(Long memberId, Long storeId, Long itemId) {
        Member member = memberRepository.findOrThrow(memberId);
        Store store = storeRepository.findOrThrow(storeId);
        validateIsOwner(member, store);

        itemRepository.delete(findOrThrow(itemId));
    }

    private void validateIsOwner(Member member, Store store) {
        if (!Objects.equals(member.getId(), store.getOwnerId())) {
            throw new IllegalArgumentException("this member is not Owner to this store");
        }
    }

    private Item findOrThrow(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
    }
}
