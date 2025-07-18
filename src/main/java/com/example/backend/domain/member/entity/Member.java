package com.example.backend.domain.member.entity;

import com.example.backend.domain.global.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    @Enumerated
    private MemberRole memberRole;

    public Member(Long id, String email, String password, String name, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.memberRole = memberRole;
    }

    public Member(String email, String password, String name, MemberRole memberRole) {
       this(null, email, password, name, memberRole);
    }
}
