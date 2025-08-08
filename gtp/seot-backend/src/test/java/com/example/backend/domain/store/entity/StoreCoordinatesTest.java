package com.example.backend.domain.store.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StoreCoordinatesTest {
    @Test
    void 초기값이_없는_상태_좌표_객체_생성() {
        StoreCoordinates storeCoordinates = new StoreCoordinates();
        assertThat(storeCoordinates.getLatitude()).isEqualTo(null);
        assertThat(storeCoordinates.getLongitude()).isEqualTo(null);
    }
}
