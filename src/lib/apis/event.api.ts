import { apiV1Client } from './index'; // v1 API 클라이언트 import

type EventCategory = "DISCOUNT_PERCENTAGE" | "DISCOUNT_AMOUNT";

/**
 * 이벤트 상세 정보 응답 타입
 */
export interface EventDetailResponse {
  id: number;
  title: string;
  description: string;
  eventCategory: EventCategory;
  startTime: string; // ISO 8601 형식의 날짜 문자열
  endTime: string;
  discountRate: number;
  discountAmount: number;
}

 /**
 * [GET] 특정 이벤트의 상세 정보를 조회합니다.
 * @param eventId - 조회할 이벤트의 ID
 */
export const getEventDetail = async (eventId: number): Promise<EventDetailResponse> => {
  const response = await apiV1Client.get<EventDetailResponse>(`/events/${eventId}`);
  return response.data;
};

/**
 * [DELETE] 특정 이벤트를 삭제합니다.
 * @param eventId - 삭제할 이벤트의 ID
 */
export const deleteEvent = async (eventId: number): Promise<void> => {
  await apiV1Client.delete(`/events/${eventId}`);
};