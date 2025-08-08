package com.example.backend.support.fixture;

import com.example.backend.domain.event.entity.Discount;
import com.example.backend.domain.event.entity.Event;
import com.example.backend.domain.event.entity.EventCategory;
import com.example.backend.domain.event.entity.EventDuration;
import com.example.backend.domain.event.entity.EventInfo;
import com.example.backend.domain.store.entity.Store;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public enum EventFixture {
    십프로_할인(new EventInfo("10% 할인", "10% 할인합니다", EventCategory.DISCOUNT_PERCENTAGE)
            , new EventDuration(LocalDateTime.of(2025, 7, 21, 10, 0), LocalDateTime.of(2025, 7, 29, 10, 0)),
            new Discount(10.0)),

    천원_할인(new EventInfo("1000원 할인", "1000원 할인합니다", EventCategory.DISCOUNT_AMOUNT)
            , new EventDuration(LocalDateTime.of(2025, 7, 21, 10, 0), LocalDateTime.of(2025, 7, 29, 10, 0)),
            new Discount(1000));

    private EventInfo eventInfo;
    private EventDuration eventDuration;
    private Discount discount;

    EventFixture(EventInfo eventInfo, EventDuration eventDuration, Discount discount) {
        this.eventInfo = eventInfo;
        this.eventDuration = eventDuration;
        this.discount = discount;
    }

    public static Event 십프로_할인(Store store) {
        return new Event(십프로_할인.getEventInfo(), 십프로_할인.getEventDuration(), 십프로_할인.getDiscount(), store);
    }

    public static Event 천원_할인(Store store) {
        return new Event(천원_할인.getEventInfo(), 천원_할인.getEventDuration(), 천원_할인.getDiscount(), store);
    }
}
