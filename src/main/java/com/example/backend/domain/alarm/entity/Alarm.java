package com.example.backend.domain.alarm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Alarm {
    @Id
    private Long id;
}
