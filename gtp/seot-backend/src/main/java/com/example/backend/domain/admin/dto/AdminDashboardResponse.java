package com.example.backend.domain.admin.dto;

import java.util.List;

public record AdminDashboardResponse(
        long totalStoreCount, // 서비스에 가입한 상점 수
        List<StoreEventVisitStats> eventStats, // 상점별/이벤트별 이용객 통계
        List<MonthlyStoreVisitStats> monthlyStats // 월별 이용객 통계
) {
}
