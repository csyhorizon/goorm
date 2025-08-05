package com.example.backend.domain.alarm.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmCommandService {
    private final AlarmReadAllService alarmReadAllService;
    private final AlarmDeleteService alarmDeleteService;
    private final AlarmCreateService alarmCreateService;
    private final AlarmReadService alarmReadService;

    public void readAll(Long memberId) {
        alarmReadAllService.readAll(memberId);
    }

    public void deleteAlarm(Long alarmId) {
        alarmDeleteService.delete(alarmId);
    }

    public void sendToUser(Long memberId, String message) {
        alarmCreateService.create(memberId, message);
    }

    public void readAlarm(Long memberId, Long alarmId) {
        alarmReadService.read(memberId, alarmId);
    }
}
