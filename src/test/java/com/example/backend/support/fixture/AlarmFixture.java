package com.example.backend.support.fixture;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.member.entity.Member;
import lombok.Getter;

@Getter
public enum AlarmFixture {
    기본알림("알림 내용", false, false),
    읽은알림("읽음 알림", true, false),
    삭제된알림("삭제 알림", false, true);

    private final String content;
    private final boolean isRead;
    private final boolean isDeleted;

    AlarmFixture(String content, boolean isRead, boolean isDeleted) {
        this.content = content;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
    }

    public Alarm toEntity(Member member) {
        Alarm alarm = Alarm.builder()
                .member(member)
                .content(content)
                .build();
        if (isRead) alarm.markAsRead();
        if (isDeleted) alarm.softDelete();
        return alarm;
    }
}

