package com.example.backend.domain.event.entity;

import com.example.backend.domain.global.BaseEntity;
import com.example.backend.domain.store.entity.Store;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private EventInfo eventInfo;

    @Embedded
    private EventDuration eventDuration;

    @Embedded
    private Discount discount;

    @ManyToOne
    private Store store;

    public Event(Long id, EventInfo eventInfo, EventDuration eventDuration, Discount discount, Store store) {
        this.id = id;
        this.eventInfo = eventInfo;
        this.eventDuration = eventDuration;
        this.discount = discount;
        this.store = store;
    }

    public Event(EventInfo eventInfo, EventDuration eventDuration, Discount discount, Store store) {
        this(null, eventInfo, eventDuration, discount, store);
    }

    public String getTitle() {
        return eventInfo.getTitle();
    }

    public String getDescription() {
        return eventInfo.getDescription();
    }

    public EventCategory getCategory() {
        return eventInfo.getEventCategory();
    }

    public LocalDateTime getStartDate() {
        return eventDuration.getStartTime();
    }

    public LocalDateTime getEndDate() {
        return eventDuration.getEndTime();
    }

    public Double getDiscountRate() {
        return discount.getDiscountRate();
    }

    public Integer getDiscountAmount() {
        return discount.getDiscountAmount();
    }
}
