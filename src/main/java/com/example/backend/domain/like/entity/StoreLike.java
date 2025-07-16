package com.example.backend.domain.like.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class StoreLike {

    @Id
    private Long id;
}
