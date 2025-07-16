package com.example.backend.domain.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Store {
    @Id
    private Long id;

    private String name;

    private String address;

    private String times;

    private String phone_number;

    private String description;

    @Enumerated
    private StoreCategory category;
}
