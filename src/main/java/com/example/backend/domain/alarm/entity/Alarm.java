package com.example.backend.domain.alarm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
// TODO: [알림 이력 확장] 추후 추가 예정
@Entity
@Getter
public class Alarm {
    @Id
    private Long id;
}
