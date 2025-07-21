package com.example.backend.domain.item.dto;

import com.example.backend.domain.item.entity.Item;

public record ItemResponse(
        Long itemId,
        String name,
        String description,
        int price,
        int discountRate) {
    public static ItemResponse from(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.getPrice(),
                item.getDiscountRate());
    }
}
