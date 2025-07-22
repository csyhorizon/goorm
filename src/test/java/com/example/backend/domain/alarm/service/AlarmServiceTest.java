package com.example.backend.domain.alarm.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.alarm.repository.AlarmRepository;
import com.example.backend.support.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ServiceTest
class AlarmServiceTest {
    @Autowired
    AlarmService alarmService;
    @Autowired
    AlarmRepository alarmRepository;

    @Test
    void 알림을_생성할_수_있다() {
        // given
        Long memberId = 100L;
        String content = "테스트 알림";

        // when
        alarmService.sendToUser(memberId, content);

        // then
        List<Alarm> alarms = alarmRepository.findByMemberIdAndIsDeletedFalseOrderByCreatedAtDesc(memberId);
        assertThat(alarms).hasSize(1);
        assertThat(alarms.get(0).getContent()).isEqualTo(content);
        assertThat(alarms.get(0).getIsRead()).isFalse();
        assertThat(alarms.get(0).getIsDeleted()).isFalse();
    }

    @Test
    void 알림을_조회할_수_있다() {
        // given
        Long memberId = 101L;
        alarmService.sendToUser(memberId, "조회 테스트 알림");

        // when
        List<Alarm> alarms = alarmService.getMyAlarms(memberId);

        // then
        assertThat(alarms).hasSize(1);
        assertThat(alarms.get(0).getContent()).isEqualTo("조회 테스트 알림");
    }

    @Test
    void 알림을_읽음처리_할_수_있다() {
        // given
        Long memberId = 102L;
        alarmService.sendToUser(memberId, "읽음처리 테스트 알림");

        // when
        alarmService.readAll(memberId);

        // then
        List<Alarm> alarms = alarmService.getMyAlarms(memberId);
        assertThat(alarms.get(0).getIsRead()).isTrue();
    }

    @Test
    void 알림을_삭제할_수_있다() {
        // given
        Long memberId = 103L;
        alarmService.sendToUser(memberId, "삭제 테스트 알림");
        Long alarmId = alarmRepository.findByMemberIdAndIsDeletedFalseOrderByCreatedAtDesc(memberId).get(0).getId();

        // when
        alarmService.deleteAlarm(alarmId);

        // then
        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms.get(0).getIsDeleted()).isTrue();
    }

    @Test
    void 안읽은_알림_개수를_조회할_수_있다() {
        // given
        Long memberId = 104L;
        alarmService.sendToUser(memberId, "1번째 알림");
        alarmService.sendToUser(memberId, "2번째 알림");

        // when
        long unreadCount = alarmService.getUnreadCount(memberId);

        // then
        assertThat(unreadCount).isEqualTo(2);
    }
}
