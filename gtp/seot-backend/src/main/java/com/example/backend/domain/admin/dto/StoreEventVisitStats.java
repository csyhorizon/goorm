package com.example.backend.domain.admin.dto;

import java.time.LocalDateTime;

public record StoreEventVisitStats(
        Long storeId,
        String storeName,
        Long eventId,
        String eventTitle,
        LocalDateTime eventStart,
        LocalDateTime eventEnd,
        Integer visitorCount
) {
}
