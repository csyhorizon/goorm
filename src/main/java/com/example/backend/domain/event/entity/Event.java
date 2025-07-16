package com.example.backend.domain.event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Event {
    @Id
    private Long id;

    @Enumerated
    private EventCategory eventCategory;
}
