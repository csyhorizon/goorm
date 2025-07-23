package com.example.backend.domain.event.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EventDuration {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public EventDuration(LocalDateTime startTime, LocalDateTime endTime) {
        validate(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validate(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }
}
