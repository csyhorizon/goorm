import { apiV1Client } from './index'; // v1 API 클라이언트 import

/**
 * 알림 정보 공통 응답 타입
 */
export interface AlarmResponse {
  id: number;
  content: string;
  isRead: boolean;
  createdAt: string; // ISO 8601 형식 또는 배열
  type: string; // [추가] 알림 타입 (예: "NEW_EVENT")
  targetUrl: string; // [추가] 클릭 시 이동할 경로
}


/**
 * [GET] 내 알림 목록을 조회합니다.
 * (memberId는 토큰을 통해 서버에서 자동으로 인식합니다)
 */
export const getAlarms = async (): Promise<AlarmResponse[]> => {
  // [수정] params에서 memberId 제거
  const response = await apiV1Client.get<AlarmResponse[]>('/notifications');
  return response.data;
};


/**
 * [GET] 안 읽은 알림 개수를 조회합니다.
 * (memberId는 토큰을 통해 서버에서 자동으로 인식합니다)
 */
export const getUnreadAlarmCount = async (): Promise<number> => {
  // [수정] params에서 memberId 제거
  const response = await apiV1Client.get<number>('/notifications/unread-count');
  return response.data;
};

/**
 * [POST] 현재 로그인한 유저의 모든 알림을 읽음 처리합니다.
 * (memberId는 토큰을 통해 서버에서 자동으로 인식합니다)
 */
export const readAllAlarms = async (): Promise<void> => {
  // [수정] params에서 memberId 제거
  await apiV1Client.post('/notifications/read-all');
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
 * (accessToken은 Axios 인터셉터 등을 통해 헤더에 자동으로 포함되어야 합니다)
 */
export const subscribeToAlarms = (): EventSource => {
  const eventSourceUrl = `${process.env.NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL}/api/v1/notifications/subscribe`;
  
  const eventSource = new EventSource(eventSourceUrl, { withCredentials: true });

  eventSource.onopen = () => {
    console.log('SSE connection opened.');
  };

  eventSource.onerror = () => {
    eventSource.close();
  };

  return eventSource;
};
