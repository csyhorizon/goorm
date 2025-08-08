package com.example.backend.domain.event.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DiscountTest {
    @Test
    void 할인금액이_음수면_예외(){
        assertThatThrownBy(() -> new Discount(-1000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({"-2","105"})
    void 할인율이_정상범위가_아니면_예외(Double value){
        assertThatThrownBy(() -> new Discount(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
