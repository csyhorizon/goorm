package com.example.backend.domain.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.domain.security.jwt.entity.RefreshToken;
import com.example.backend.domain.member.entity.Member;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(Member user);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(Member user);
}
