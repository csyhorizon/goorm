package com.example.backend.domain.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Post {
    @Id
    private Long id;

    private String title;

    private String content;
}
