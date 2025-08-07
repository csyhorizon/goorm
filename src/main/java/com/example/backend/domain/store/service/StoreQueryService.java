package com.example.backend.domain.store.service;

import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryService {
    private final StoreRepository storeRepository;

    public StoreResponse findById(Long storeId) {
        Store store = storeRepository.findOrThrow(storeId);
        return StoreResponse.from(store);
    }

    public StoreResponse findByMemberId(Long memberId) {
        return StoreResponse.from(storeRepository.findByMemberId(memberId));
    }

    public List<StoreResponse> findAll() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(StoreResponse::from)
                .toList();
    }
}
