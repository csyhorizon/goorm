package com.example.backend.domain.item.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Item {
    @Id
    private Long id;

    private String name;

    private int price;

    private int discountRate;
}
