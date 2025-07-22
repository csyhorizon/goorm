package com.example.backend.domain.item.entity;

import com.example.backend.domain.global.BaseEntity;
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
public class Item extends BaseEntity {
    private static final int MIN_PRICE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int price;


    @ManyToOne
    private Store store;

    public Item(Long id, String name, String description, int price, Store store) {
        validateAll(name, price);
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.store = store;
    }

    public Item(String name, String description, int price, Store store) {
        this(null, name, description, price, store);
    }

    private void validateAll(String name, int price) {
        validateName(name);
        validatePrice(price);
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
}
