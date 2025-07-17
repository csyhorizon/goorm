package com.example.backend.domain.store.service;

import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.store.dto.StoreCreateRequest;
import com.example.backend.domain.store.dto.StoreResponse;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.repository.StoreRepository;
import com.example.backend.domain.user.entity.Users;
import com.example.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public StoreResponse save(Long userId, StoreCreateRequest storeCreateRequest) {
        Users user = userRepository.findOrThrow(userId);
        Store store = storeRepository.save(storeCreateRequest.toEntity(user));

        return StoreResponse.from(store);
    }
}
