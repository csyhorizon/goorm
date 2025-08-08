package com.example.backend.support.fixture;

import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.store.entity.StoreCategory;
import com.example.backend.domain.store.entity.StoreDuration;
import com.example.backend.domain.member.entity.Member;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public enum StoreFixture {
    과일가게("과일가게", "서울시 테스트구", "02-2222-2222", "과일가게 입니다.",
            StoreCategory.FRUIT_SHOP, new StoreDuration(LocalTime.of(9, 0), LocalTime.of(21, 0)));
    private String name;
    private String address;
    private String phone_number;
    private String description;
    private StoreCategory category;
    private StoreDuration storeDuration;

    StoreFixture(String name, String address, String phone_number, String description, StoreCategory category,
                 StoreDuration storeDuration) {
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.description = description;
        this.category = category;
        this.storeDuration = storeDuration;
    }

    public static Store 과일가게(Member user) {
        return new Store(과일가게.name, 과일가게.address, 과일가게.phone_number, 과일가게.description, 과일가게.category,
                과일가게.storeDuration, user);
    }
}
