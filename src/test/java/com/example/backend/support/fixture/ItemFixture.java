package com.example.backend.support.fixture;

import com.example.backend.domain.item.entity.Item;
import com.example.backend.domain.store.entity.Store;
import lombok.Getter;

@Getter
public enum ItemFixture {
    사과("사과", "사과입니다.", 1500, 0),
    바나나("바나나", "바나나입니다.", 2500, 0),
    멜론("멜론", "멜론입니다.", 12000, 10),
    수박("수박", "수박입니다.", 15000, 15);

    private String name;
    private String description;
    private int price;
    private int discountRate;

    ItemFixture(String name, String description, int price, int discountRate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static Item 사과(Store store) {
        return new Item(사과.getName(), 사과.getDescription(), 사과.getPrice(), 사과.discountRate, store);
    }

    public static Item 바나나(Store store) {
        return new Item(바나나.getName(), 바나나.getDescription(), 바나나.getPrice(), 바나나.discountRate, store);
    }

    public static Item 멜론(Store store) {
        return new Item(멜론.getName(), 멜론.getDescription(), 멜론.getPrice(), 멜론.discountRate, store);
    }

    public static Item 수박(Store store) {
        return new Item(수박.getName(), 수박.getDescription(), 수박.getPrice(), 수박.discountRate, store);
    }
}
