package com.example.backend.domain.store.service.command;

import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.domain.store.dto.CoordinateRequest;
import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoordinateCreateService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public StoreResponse insertCoordinates(Long memberId, Long storeId, CoordinateRequest coordinateRequest) {
        Member member = memberRepository.findOrThrow(memberId);
        Store store = storeRepository.findOrThrow(storeId);

        validateIsOwner(member, store);
        store.updateCoordinates(coordinateRequest.toStoreCoordinates());
        return StoreResponse.from(store);
    }

    private void validateIsOwner(Member member, Store store) {
        if (!Objects.equals(store.getOwnerId(), member.getId())) {
            throw new IllegalArgumentException("this user is not owner of this store");
        }
    }
}
