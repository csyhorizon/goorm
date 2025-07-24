package com.example.backend.domain.alarm.repository;

import com.example.backend.domain.alarm.entity.Alarm;
import com.example.backend.domain.member.entity.Member;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    // 안 읽은 알림 개수
    @Query("SELECT COUNT(a) FROM Alarm a WHERE a.member = :member AND a.isRead = false AND a.isDeleted = false")
    long countUnreadByMember(@Param("member") Member member);

    // 전체 알림 읽음 처리
    @Modifying
    @Query("UPDATE Alarm a SET a.isRead = true WHERE a.member = :member AND a.isRead = false AND a.isDeleted = false")
    void markAllReadByMember(@Param("member") Member member);

    // 개별 소프트 삭제
    @Modifying
    @Query("UPDATE Alarm a SET a.isDeleted = true WHERE a.id = :alarmId")
    void softDeleteById(@Param("alarmId") Long alarmId);

    // 내 알림 리스트
    List<Alarm> findByMemberAndIsDeletedFalseOrderByCreatedAtDesc(Member member);
}
