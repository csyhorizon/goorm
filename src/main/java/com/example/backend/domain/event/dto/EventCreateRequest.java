package com.example.backend.domain.event.dto;

import com.example.backend.domain.event.entity.Discount;
import com.example.backend.domain.event.entity.Event;
import com.example.backend.domain.event.entity.EventCategory;
import com.example.backend.domain.event.entity.EventDuration;
import com.example.backend.domain.event.entity.EventInfo;
import com.example.backend.domain.store.entity.Store;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record EventCreateRequest(
        String title,
        String description,
        EventCategory eventCategory,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double discountRate,
        Integer discountAmount
) {
    public Event toEntity(Store store) {
        return new Event(
                new EventInfo(title, description, eventCategory),
                new EventDuration(startTime, endTime),
                createDiscount(eventCategory), store);
    }

    public Discount createDiscount(EventCategory eventCategory) {
        if (eventCategory.equals(EventCategory.DISCOUNT_AMOUNT)) {
            return new Discount(null, discountAmount);
        }

        if (eventCategory.equals(EventCategory.DISCOUNT_PERCENTAGE)) {
            return new Discount(discountRate, null);
        }

        return new Discount(null, null);
    }
}
