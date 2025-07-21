package com.example.backend.support.fixture;

import com.example.backend.domain.item.entity.Item;
import com.example.backend.domain.store.entity.Store;
import lombok.Getter;

@Getter
public enum ItemFixture {
    사과("사과", 1500, 0),
    바나나("바나나", 2500, 0),
    멜론("멜론", 12000, 10),
    수박("수박", 15000, 15);

    private String name;
    private int price;
    private int discountRate;

    ItemFixture(String name, int price, int discountRate) {
        this.name = name;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static Item 사과(Store store) {
        return new Item(사과.getName(), 사과.getPrice(), 사과.discountRate, store);
    }

    public static Item 바나나(Store store) {
        return new Item(바나나.getName(), 바나나.getPrice(), 바나나.discountRate, store);
    }

    public static Item 멜론(Store store) {
        return new Item(멜론.getName(), 멜론.getPrice(), 멜론.discountRate, store);
    }

    public static Item 수박(Store store) {
        return new Item(수박.getName(), 수박.getPrice(), 수박.discountRate, store);
    }
}
