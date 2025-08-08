package com.example.backend.domain.event.entity;

import lombok.Getter;

@Getter
public enum EventCategory {
    DISCOUNT_PERCENTAGE, // 할인율(%) 할인
    DISCOUNT_AMOUNT,     // 할인금액(원)
    GIFT;                // 증정이벤트
}
