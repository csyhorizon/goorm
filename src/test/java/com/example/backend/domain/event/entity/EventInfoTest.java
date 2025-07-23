package com.example.backend.domain.event.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EventInfoTest {
    @Test
    void 제목이_없으면_예외() {
        assertThatThrownBy(() -> new EventInfo("", "", EventCategory.GIFT))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
