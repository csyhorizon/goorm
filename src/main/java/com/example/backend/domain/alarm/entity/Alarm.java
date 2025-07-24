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
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;           // 알림 받는 회원

    @Column(length = 1000)
    private String content;          // 알림 내용

    private Boolean isRead = false;  // 읽음 여부

    private Boolean isDeleted = false; // 삭제 여부

    @Builder
    public Alarm(Member member, String content) {
        this.member = member;
        this.content = content;
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
