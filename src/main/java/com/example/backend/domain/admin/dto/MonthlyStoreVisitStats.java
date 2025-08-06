package com.example.backend.domain.admin.dto;

public record MonthlyStoreVisitStats(
        Long storeId,
        String storeName,
        Integer year,
        Integer month,
        Integer visitorCount
) {
}
