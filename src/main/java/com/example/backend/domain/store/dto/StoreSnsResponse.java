package com.example.backend.domain.store.dto;

public record StoreSnsResponse(
        Long storeId,
        String instagramUrl,
        String blogUrl
) {
}
