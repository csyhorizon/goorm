package com.example.backend.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.domain.user.entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
