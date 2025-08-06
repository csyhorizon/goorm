package com.example.backend.domain.alarm.entity;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Entity
@Getter
@NoArgsConstructor
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;           // 알림 받는 회원

    @Column(length = 1000)
    private String content;          // 알림 내용

    private Boolean isRead = false;  // 읽음 여부

    private Boolean isDeleted = false; // 삭제 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType type;          // 알림 유형

    @Column(length = 2048)
    private String targetUrl;

    @Builder
    public Alarm(Member member, String content, AlarmType type, String targetUrl) {
        this.member = member;
        this.content = content;
        this.type = type;
        this.targetUrl = targetUrl;
        this.isRead = false;
        this.isDeleted = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}