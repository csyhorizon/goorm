package com.example.backend.domain.store.dto;

import com.example.backend.domain.store.entity.StoreCoordinates;

public record CoordinateRequest(
        double latitude,
        double longitude
) {
    public StoreCoordinates toStoreCoordinates() {
        return new StoreCoordinates(latitude, longitude);
    }
}
