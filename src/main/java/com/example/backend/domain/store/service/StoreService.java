package com.example.backend.domain.store.service;

import com.example.backend.domain.store.dto.StoreCreateRequest;
import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public StoreResponse save(Long memberId, StoreCreateRequest storeCreateRequest) {
        Member member = memberRepository.findOrThrow(memberId);
        validateOwnerRole(member);
        Store store = storeRepository.save(storeCreateRequest.toEntity(member));
        return StoreResponse.from(store);
    }

    private static void validateOwnerRole(Member member) {
        if (!member.isOwner()){
            throw new IllegalArgumentException("this member is not owner");
        }
    }

    @Transactional(readOnly = true)
    public StoreResponse findById(Long storeId) {
        Store store = storeRepository.findOrThrow(storeId);
        return StoreResponse.from(store);
    }

    public void delete(Long storeId) {
        Store store = storeRepository.findOrThrow(storeId);
        storeRepository.delete(store);
    }
}
