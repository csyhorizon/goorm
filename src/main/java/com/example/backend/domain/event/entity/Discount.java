package com.example.backend.domain.event.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Discount {
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MIN_DISCOUNT_RATE = 0;
    private static final int MAX_DISCOUNT_RATE = 100; // 할인율의 최대값은 100%

    private Double discountRate;     // 할인율
    private Integer discountAmount;  // 할인금액

    public Discount(Double discountRate, Integer discountAmount) {
        validateAll(discountRate, discountAmount);
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
    }

    public Discount(Double discountRate) {
        this(discountRate, null);
    }

    public Discount(Integer discountAmount) {
        this(null, discountAmount);
    }

    private void validateAll(Double discountRate, Integer discountAmount){
        if(discountRate != null){
            validateDiscountRate(discountRate);
        }
        if(discountAmount != null){
            validateDiscountAmount(discountAmount);
        }
    }

    private void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("Discount amount cannot be negative");
        }
    }

    private void validateDiscountRate(Double discountRate) {
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("Discount rate cannot be less than "
                    + MIN_DISCOUNT_RATE + " or greater than " + MAX_DISCOUNT_RATE);
        }
    }
}
