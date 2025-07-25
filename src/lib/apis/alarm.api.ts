import { apiV1Client } from './index'; // v1 API 클라이언트 import

/**
 * 알림 정보 공통 응답 타입
 */
export interface AlarmResponse {
  id: number;
  content: string;
  isRead: boolean;
  isDeleted: boolean;
  createdAt: string; // ISO 8601 형식의 날짜 문자열
}


/**
 * [GET] 내 알림 목록을 조회합니다.
 * @param memberId - 회원 ID
 */
export const getAlarms = async (memberId: number): Promise<AlarmResponse[]> => {
  const response = await apiV1Client.get<AlarmResponse[]>('/notifications', {
    params: { memberId },
  });
  return response.data;
};


/**
 * [GET] 안 읽은 알림 개수를 조회합니다.
 * @param memberId - 회원 ID
 */
export const getUnreadAlarmCount = async (memberId: number): Promise<number> => {
  const response = await apiV1Client.get<number>('/notifications/unread-count', {
    params: { memberId },
  });
  return response.data;
};

/**
 * [POST] 특정 회원의 모든 알림을 읽음 처리합니다.
 * @param memberId - 회원 ID
 */
export const readAllAlarms = async (memberId: number): Promise<void> => {
  await apiV1Client.post('/notifications/read-all', null, { // body가 없으므로 null 전달
    params: { memberId },
  });
};


/**
 * [DELETE] 알림을 개별 삭제합니다.
 * @param alarmId - 삭제할 알림 ID
 */
export const deleteAlarm = async (alarmId: number): Promise<void> => {
  await apiV1Client.delete(`/notifications/${alarmId}`);
};




/**
 * [SSE] 실시간 알림 SSE 구독을 요청합니다.
 * * @param memberId - 구독할 회원 ID
 * @param accessToken - 인증을 위한 JWT 토큰
 * @returns {EventSource} - SSE 연결 객체
 */
export const subscribeToAlarms = (memberId: number, accessToken: string): EventSource => {
  const eventSourceUrl = `${process.env.NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL}/api/v1/notifications/subscribe?memberId=${memberId}&token=${accessToken}`;
  
  const eventSource = new EventSource(eventSourceUrl);

  eventSource.onopen = () => {
    console.log('SSE connection opened.');
  };

  eventSource.onerror = (error) => {
    console.error('SSE connection error:', error);
    eventSource.close();
  };

  return eventSource;
};