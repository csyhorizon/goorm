package com.example.backend.domain.alarm.service.command;

import com.example.backend.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmDeleteService {
    private final AlarmRepository alarmRepository;

    @Transactional
    public void delete(Long alarmId) {
        alarmRepository.softDeleteById(alarmId);
    }
}
