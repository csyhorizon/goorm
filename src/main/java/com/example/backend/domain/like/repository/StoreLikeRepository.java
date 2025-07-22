package com.example.backend.domain.like.repository;

import com.example.backend.domain.like.entity.StoreLike;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreLikeRepository extends JpaRepository<StoreLike,Long> {
    Optional<StoreLike> findByMemberAndStore(Member member, Store store);
    List<StoreLike> findAllByMember(Member member);
}
