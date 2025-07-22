package com.example.backend.domain.event.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class EventDurationTest {

    @Test
    void 종료일이_시작일_이전이면_예외() {
        LocalDateTime startTime = LocalDateTime.of(2025, 7, 21, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 7, 21, 12, 0);
        assertThatThrownBy(() -> new EventDuration(startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
