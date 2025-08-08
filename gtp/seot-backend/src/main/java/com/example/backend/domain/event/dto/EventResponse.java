package com.example.backend.domain.event.dto;

import com.example.backend.domain.event.entity.Event;
import com.example.backend.domain.event.entity.EventCategory;
import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        EventCategory eventCategory,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double discountRate,
        Integer discountAmount) {

    public static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getTitle(), event.getDescription(), event.getCategory(),
                event.getStartDate(), event.getEndDate(), event.getDiscountRate(), event.getDiscountAmount());
    }
}
