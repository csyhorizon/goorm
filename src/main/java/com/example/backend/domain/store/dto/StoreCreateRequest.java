package com.example.backend.domain.store.dto;

import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.entity.StoreCategory;
import com.example.backend.domain.store.entity.StoreDuration;
import com.example.backend.domain.user.entity.Users;
import java.time.LocalTime;

public record StoreCreateRequest(
        String name,
        String address,
        String phone_number,
        String description,
        StoreCategory category,
        LocalTime startDate,
        LocalTime endDate
) {
    public Store toEntity(Users user) {
        return new Store(name, address, phone_number, description, category,
                new StoreDuration(startDate, endDate), user);
    }
}
