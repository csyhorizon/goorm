package com.example.backend.domain.store.repository;

import com.example.backend.domain.admin.dto.MonthlyStoreVisitStats;
import com.example.backend.domain.admin.dto.StoreEventVisitStats;

import java.util.List;

public interface StoreDailyVisitorCustom {
    long countJoinedStores();
    List<StoreEventVisitStats> getStoreEventVisitStats();
    List<MonthlyStoreVisitStats> getMonthlyVisitStats();
}
