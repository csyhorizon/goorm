package com.example.backend.domain.admin.service;

import com.example.backend.domain.admin.dto.AdminDashboardResponse;
import com.example.backend.domain.admin.dto.MonthlyStoreVisitStats;
import com.example.backend.domain.admin.dto.StoreEventVisitStats;
import com.example.backend.domain.store.repository.StoreDailyVisitorRepository;
import com.example.backend.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final StoreDailyVisitorRepository storeDailyVisitorRepository;

    public AdminDashboardResponse getAdminDashboard() {
        long visitorCount = storeDailyVisitorRepository.countJoinedStores();
        List<StoreEventVisitStats> eventVisitStats = storeDailyVisitorRepository.getStoreEventVisitStats();
        List<MonthlyStoreVisitStats> monthlyVisitStats = storeDailyVisitorRepository.getMonthlyVisitStats();

        return new AdminDashboardResponse(visitorCount, eventVisitStats, monthlyVisitStats);
    }
}
