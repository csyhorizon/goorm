package com.example.backend.domain.store.entity;

import com.example.backend.domain.global.BaseEntity;
import com.example.backend.domain.member.entity.Member;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store extends BaseEntity {
    @Id
    private Long id;

    private String name;

    private String address;

    private String phone_number;

    private String description;

    @Enumerated
    private StoreCategory category;

    @Embedded
    private StoreDuration storeDuration;

    @ManyToOne
    private Member member;

    public Store(String name, String address, String phone_number, String description, StoreCategory category,
                 StoreDuration storeDuration, Member member) {
        this(null, name, address, phone_number, description, category, storeDuration, member);
    }

    public Store(Long id, String name, String address, String phone_number, String description, StoreCategory category,
                 StoreDuration storeDuration, Member member) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.description = description;
        this.category = category;
        this.storeDuration = storeDuration;
        this.member = member;
    }

    public LocalDate getStartDate() {
        return storeDuration.getStartDate();
    }

    public LocalDate getEndDate() {
        return storeDuration.getEndDate();
    }
}
