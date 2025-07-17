package com.example.backend.domain.store.entity;

import com.example.backend.domain.global.BaseEntity;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.user.entity.Users;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone_number;

    private String description;

    @Enumerated
    private StoreCategory category;

    @Embedded
    private StoreDuration storeDuration;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users_id")
    private Users user;

    public Store(String name, String address, String phone_number, String description, StoreCategory category,
                 StoreDuration storeDuration, Users user) {
        this(null, name, address, phone_number, description, category, storeDuration, user);
    }

    public Store(Long id, String name, String address, String phone_number, String description, StoreCategory category,
                 StoreDuration storeDuration, Users user) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.description = description;
        this.category = category;
        this.storeDuration = storeDuration;
        this.user = user;
    }

    public LocalTime getStartTime() {
        return storeDuration.getStartTime();
    }

    public LocalTime getEndTime() {
        return storeDuration.getEndTime();
    }
}
