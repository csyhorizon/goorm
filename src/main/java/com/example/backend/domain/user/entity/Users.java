package com.example.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.backend.domain.global.BaseEntity;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Users extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Users(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
