package com.example.backend.domain.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.backend.domain.member.entity.Member;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
  
    public void updateToken(String token, LocalDateTime expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }
}
