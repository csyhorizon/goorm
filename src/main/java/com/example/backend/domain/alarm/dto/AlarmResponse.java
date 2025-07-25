package com.example.backend.domain.alarm.dto;

import com.example.backend.domain.alarm.entity.Alarm;
import java.time.LocalDateTime;
import java.util.List;

public record AlarmResponse(
        Long id,
        String content,
        Boolean isRead,
        LocalDateTime createdAt
) {

    public static AlarmResponse from(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getContent(),
                alarm.getIsRead(),
                alarm.getCreatedAt()
        );
    }

    public static List<AlarmResponse> from(List<Alarm> alarms) {
        return alarms.stream()
                .map(AlarmResponse::from)
                .toList();
    }
}
