package com.example.backend.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id
    private Long id;

    private String email;

    private String password;

    private String name;

    @Enumerated
    private MemberRole memberRole;
}
