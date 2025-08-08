package com.example.backend.domain.item.dto;

import com.example.backend.domain.item.entity.Item;

public record ItemResponse(
        Long itemId,
        String name,
        String description,
        int price) {

    public static ItemResponse from(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.getPrice());
    }

    public static ItemResponse of(Item item, int price) {
        return new ItemResponse(item.getId(), item.getName(), item.getDescription(), price);
    }
}
