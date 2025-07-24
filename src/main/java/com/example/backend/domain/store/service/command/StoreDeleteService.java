package com.example.backend.domain.store.service.command;

import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreDeleteService {
    private final StoreRepository storeRepository;

    public void delete(Long storeId) {
        Store store = storeRepository.findOrThrow(storeId);
        storeRepository.delete(store);
    }
}
