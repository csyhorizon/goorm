package com.example.backend.domain.item.entity;

import com.example.backend.domain.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Item {
    private static final int DEFAULT_DISCOUNT_RATE = 0;
    private static final int MIN_PRICE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int discountRate;

    @ManyToOne
    private Store store;

    public Item(Long id, String name, int price, int discountRate, Store store) {
        validateAll(name, price, discountRate);
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountRate = discountRate;
        this.store = store;
    }

    public Item(String name, int price, Store store) {
        this(null, name, price, DEFAULT_DISCOUNT_RATE, store);
    }

    public Item(String name, int price, int discountRate, Store store) {
        this(null, name, price, discountRate, store);
    }

    private void validateAll(String name, int price, int discountRate) {
        validateName(name);
        validatePrice(price);
        validateDiscountRate(discountRate);
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
    }

    private void validatePrice(int price) {
        if (price < MIN_PRICE) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    private void validateDiscountRate(int discountRate) {
        if (discountRate < MIN_PRICE) {
            throw new IllegalArgumentException("Discount rate cannot be negative");
        }
    }
}
