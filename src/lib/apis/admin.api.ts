import { apiV1Client } from './index';

/**
 * 상점별/이벤트별 이용객 통계 정보
 */
export interface StoreEventVisitStats {
  storeId: number;
  storeName: string;
  eventId: number;
  eventTitle: string;
  eventStart: string; // ISO 8601 형식의 날짜 문자열
  eventEnd: string;   // ISO 8601 형식의 날짜 문자열
  visitorCount: number;
}

/**
 * 상점별 월간 이용객 통계 정보
 */
export interface MonthlyStoreVisitStats {
  storeId: number;
  storeName: string;
  year: number;
  month: number;
  visitorCount: number;
}

/**
 * 관리자 대시보드 응답 데이터 타입
 */
export interface AdminDashboardResponse {
  totalStoreCount: number;
  eventStats: StoreEventVisitStats[];
  monthlyStats: MonthlyStoreVisitStats[];
}

/**
 * [GET] 관리자 대시보드 데이터를 조회합니다.
 * @returns 대시보드 데이터 객체
 */
export const getAdminDashboardStats = async (): Promise<AdminDashboardResponse> => {
  const response = await apiV1Client.get<AdminDashboardResponse>(
    `/admin/dashboard`
  );
  return response.data;
};
