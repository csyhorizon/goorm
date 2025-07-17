package com.example.backend.domain.store.dto;

import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.entity.StoreCategory;
import java.time.LocalDate;
import java.time.LocalTime;

public record StoreResponse(
        Long id,
        String name,
        String address,
        String phone_number,
        String description,
        StoreCategory category,
        LocalTime startDate,
        LocalTime endDate
) {
    public static StoreResponse from(Store store) {
        return new StoreResponse(store.getId(), store.getName(), store.getAddress(), store.getPhone_number(),
                store.getDescription(), store.getCategory(), store.getStartTime(), store.getEndTime());
    }
}
