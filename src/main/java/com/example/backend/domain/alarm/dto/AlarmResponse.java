package com.example.backend.domain.alarm.dto;

import com.example.backend.domain.alarm.entity.Alarm;
import java.time.LocalDateTime;

public record AlarmResponse(
        Long id,
        String content,
        Boolean isRead,
        Boolean isDeleted,
        LocalDateTime createdAt
) {
    public static AlarmResponse from(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getContent(),
                alarm.getIsRead(),
                alarm.getIsDeleted(),
                alarm.getCreatedAt()
        );
    }
}
