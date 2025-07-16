package com.example.backend.domain.like.repository;

import com.example.backend.domain.like.entity.StoreLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreLikeRepository extends JpaRepository<StoreLike,Long> {
}
