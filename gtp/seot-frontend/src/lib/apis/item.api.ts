import { apiV1Client } from './index'; // v1 API 클라이언트 import

/**
 * 상품 상세 정보 응답 타입
 */
export interface ItemDetailResponse {
  itemId: number;
  name: string;
  description: string;
  price: number;
}


/**
 * [GET] 특정 판매 상품의 상세 정보를 조회합니다.
 * @param itemId - 조회할 상품의 ID
 */
export const getItemDetail = async (itemId: number): Promise<ItemDetailResponse> => {
  const response = await apiV1Client.get<ItemDetailResponse>(`/items/${itemId}`);
  return response.data;
};