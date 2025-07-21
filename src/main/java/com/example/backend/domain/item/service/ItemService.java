package com.example.backend.domain.item.service;

import com.example.backend.domain.item.dto.ItemCreateRequest;
import com.example.backend.domain.item.repository.ItemRepository;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.repository.StoreRepository;
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
        Member
    }
}
