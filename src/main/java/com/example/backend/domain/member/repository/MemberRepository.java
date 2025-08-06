package com.example.backend.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    default Member findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found")); // 에러코드 추후 통일화 필요
    }
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
}
