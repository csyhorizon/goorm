package com.example.backend.domain.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDailyVisitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    private LocalDateTime visitDate;

    private int paymentCount; //결제(이용객) 수

    public StoreDailyVisitor(Long id, Store store, LocalDateTime visitDate, int paymentCount) {
        this.id = id;
        this.store = store;
        this.visitDate = visitDate;
        this.paymentCount = paymentCount;
    }
}
