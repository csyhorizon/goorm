package com.example.backend.domain.alarm.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.alarm.repository.AlarmRepository;
import com.example.backend.domain.alarm.service.command.AlarmCommandService;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.member.repository.MemberRepository;
import com.example.backend.support.annotation.ServiceTest;
import com.example.backend.support.fixture.MemberFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ServiceTest
class AlarmServiceTest {
    @Autowired
    AlarmCommandService alarmCommandService;
    @Autowired
    AlarmQueryService alarmQueryService;
    @Autowired
    AlarmRepository alarmRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 알림을_생성할_수_있다() {
        // given
        Member member = MemberFixture.김회원();
        memberRepository.save(member);

        // when
        alarmCommandService.sendToUser(member.getId(), "테스트 알림");

        // then
        List<Alarm> alarms = alarmQueryService.getMyAlarms(member.getId());
        assertThat(alarms).hasSize(1);
        assertThat(alarms.get(0).getContent()).isEqualTo("테스트 알림");
        assertThat(alarms.get(0).getIsRead()).isFalse();
        assertThat(alarms.get(0).getIsDeleted()).isFalse();
    }

    @Test
    void 알림을_읽음처리_할_수_있다() {
        // given
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        alarmCommandService.sendToUser(member.getId(), "읽음처리 테스트 알림");

        // when
        alarmCommandService.readAll(member.getId());

        // then
        List<Alarm> alarms = alarmQueryService.getMyAlarms(member.getId());
        assertThat(alarms.get(0).getIsRead()).isTrue();
    }

    @Test
    void 알림을_삭제할_수_있다() {
        // given
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        alarmCommandService.sendToUser(member.getId(), "삭제 테스트 알림");
        Long alarmId = alarmQueryService.getMyAlarms(member.getId()).get(0).getId();

        // when
        alarmCommandService.deleteAlarm(alarmId);

        // then
        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms.get(0).getIsDeleted()).isTrue();
    }

    @Test
    void 안읽은_알림_개수를_조회할_수_있다() {
        // given
        Member member = MemberFixture.김회원();
        memberRepository.save(member);
        alarmCommandService.sendToUser(member.getId(), "1번째 알림");
        alarmCommandService.sendToUser(member.getId(), "2번째 알림");

        // when
        long unreadCount = alarmQueryService.getUnreadCount(member.getId());

        // then
        assertThat(unreadCount).isEqualTo(2);
    }
}
