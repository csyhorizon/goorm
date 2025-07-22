package com.example.backend.domain.event.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EventInfo {
    private String title;

    private String description;

    @Enumerated
    private EventCategory eventCategory;

    public EventInfo(String title, String description, EventCategory eventCategory) {
        this.title = title;
        this.description = description;
        this.eventCategory = eventCategory;
    }
}
