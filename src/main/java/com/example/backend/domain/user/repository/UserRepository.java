package com.example.backend.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.domain.user.entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    default Users findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found")); // 에러코드 추후 통일화 필요
    }
    Optional<Users> findByUsername(String username);
}
