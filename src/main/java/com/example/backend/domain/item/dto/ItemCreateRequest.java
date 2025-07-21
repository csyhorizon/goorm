package com.example.backend.domain.item.dto;

import com.example.backend.domain.item.entity.Item;
import com.example.backend.domain.store.entity.Store;

public record ItemCreateRequest(String name, int price) {
    public Item toEntity(Store store) {
        return new Item(name, price, store);
    }
}
