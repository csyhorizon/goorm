package com.example.backend.domain.store.repository;

import com.example.backend.domain.store.entity.StoreDailyVisitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDailyVisitorRepository extends JpaRepository<StoreDailyVisitor, Long> , StoreDailyVisitorCustom{
}
