package com.example.backend.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.backend.domain.global.BaseEntity;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Member(Long id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Member(String username, String email, String password, Role role) {
        this(null, username, email, password, role);
    }

    public boolean isOwner() {
        return role == Role.OWNER;
    }
}
