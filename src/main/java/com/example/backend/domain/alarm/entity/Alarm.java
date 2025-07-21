package com.example.backend.domain.alarm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;           // 알림 받는 회원

    private String content;          // 알림 내용

    private Boolean isRead = false;  // 읽음 여부

    private Boolean isDeleted = false; // 삭제 여부

    private LocalDateTime createdAt; // 생성 시각

    @Builder
    public Alarm(Long memberId, String content) {
        this.memberId = memberId;
        this.content = content;
        this.isRead = false;
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}
