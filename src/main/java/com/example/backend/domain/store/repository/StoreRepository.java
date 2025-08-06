package com.example.backend.domain.store.repository;

import com.example.backend.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    default Store findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found")); // 에러코드 추후 통일화 필요
    }

    Store findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
