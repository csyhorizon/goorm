package com.example.backend.domain.store.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StoreDuration {
    private LocalTime startTime;
    private LocalTime endTime;

    public StoreDuration(LocalTime startTime, LocalTime endTime) {
        validate(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validate(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }
}
