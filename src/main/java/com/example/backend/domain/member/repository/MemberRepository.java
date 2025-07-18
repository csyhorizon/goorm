package com.example.backend.domain.member.repository;

import com.example.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    default Member findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found")); // 에러코드 추후 통일화 필요
    }
}
