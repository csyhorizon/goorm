import { apiV1Client } from './index'; // v1 API 클라이언트 import

/**
 * 가게 좋아요 요청 시 Body
 */
export interface LikeStoreRequest {
  storeId: number;
}

/**
 * 가게 좋아요 응답 타입
 */
export interface LikeStoreResponse {
  becomeLike: boolean; // true: 좋아요 상태, false: 좋아요 취소 상태
}

/**
 * [POST] 특정 가게에 대한 '좋아요' 상태를 토글(추가/취소)합니다.
 * @param request - 좋아요할 가게의 ID를 담은 객체
 */
export const toggleStoreLike = async (request: LikeStoreRequest): Promise<LikeStoreResponse> => {
  const response = await apiV1Client.post<LikeStoreResponse>('/StoreLike', request);
  return response.data;
};