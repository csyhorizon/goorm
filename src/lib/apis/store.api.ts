import { apiV1Client } from ".";

export type StoreCategory = "CAFE" | "RESTAURANT" | "PARK" | "SHOPPING" | "TOURIST_ATTRACTION" | "HOSPITAL" | "FRUIT_SHOP" | "OTHER";

// UI에서 사용할 카테고리 옵션과 값을 매핑하는 상수
export const STORE_CATEGORY_OPTIONS = [
  { value: "CAFE", label: "카페" },
  { value: "RESTAURANT", label: "음식점" },
  { value: "PARK", label: "공원" },
  { value: "SHOPPING", label: "쇼핑" },
  { value: "TOURIST_ATTRACTION", label: "관광지" },
  { value: "HOSPITAL", label: "병원" },
  { value: "FRUIT_SHOP", label: "과일가게" },
  { value: "OTHER", label: "기타" },
];

type TimeOfDay = {
  hour: number;
  minute: number;
  second: number;
  nano: number;
};

type EventCategory = "DISCOUNT_PERCENTAGE" | "DISCOUNT_AMOUNT";

export type CreateStoreRequest = {
  name: string;
  address: string;
  phone_number: string;
  description: string;
  category: StoreCategory;
  startDate: string;
  endDate: string;
};

export type StoreResponse = {
  id: number;
  name: string;
  address: string;
  phone_number: string;
  description: string;
  category: StoreCategory;
  startDate: TimeOfDay;
  endDate: TimeOfDay;
};

export type ItemResponse = {
  itemId: number;
  name: string;
  description: string;
  price: number;
};

export type CreateItemRequest = {
  name: string;
  description: string;
  price: number;
};

export type EventResponse = {
  id: number;
  title: string;
  description: string;
  eventCategory: EventCategory;
  startTime: string; // ISO 8601 형식의 날짜 문자열
  endTime: string;
  discountRate: number;
  discountAmount: number;
};

export type CreateEventRequest = {
  title: string;
  description: string;
  eventCategory: EventCategory;
  startTime: string;
  endTime: string;
  discountRate: number;
  discountAmount: number;
};


/**
 * [POST] 가게를 등록합니다.
 */
export const createStore = async (storeData: CreateStoreRequest): Promise<StoreResponse> => {
  const response = await apiV1Client.post('/stores', storeData);
  return response.data;
};

/**
 * [GET] 가게의 상세 정보를 조회합니다.
 */
export const getStoreDetail = async (storeId: number): Promise<StoreResponse> => {
  const response = await apiV1Client.get(`/stores/${storeId}`);
  return response.data;
};

/**
 * [DELETE] 가게를 삭제합니다.
 */
export const deleteStore = async (storeId: number): Promise<void> => {
  await apiV1Client.delete(`/stores/${storeId}`);
};

/**
 * [GET] 가게의 모든 상품 목록을 조회합니다.
 */
export const getStoreItems = async (storeId: number): Promise<ItemResponse[]> => {
  const response = await apiV1Client.get(`/stores/${storeId}/items`);
  return response.data;
};

/**
 * [POST] 가게에 새로운 상품을 등록합니다.
 */
export const createStoreItem = async (storeId: number, itemData: CreateItemRequest): Promise<ItemResponse> => {
  const response = await apiV1Client.post(`/stores/${storeId}/items`, itemData);
  return response.data;
};

/**
 * [DELETE] 가게의 특정 상품을 삭제합니다.
 */
export const deleteStoreItem = async (storeId: number, itemId: number): Promise<void> => {
  await apiV1Client.delete(`/stores/${storeId}/items/${itemId}`);
};

/**
 * [GET] 가게의 모든 이벤트 목록을 조회합니다.
 */
export const getStoreEvents = async (storeId: number): Promise<EventResponse[]> => {
  const response = await apiV1Client.get(`/stores/${storeId}/events`);
  return response.data;
};

/**
 * [POST] 가게에 새로운 이벤트를 등록합니다.
 */
export const createStoreEvent = async (storeId: number, eventData: CreateEventRequest): Promise<EventResponse> => {
  const response = await apiV1Client.post(`/stores/${storeId}/events`, eventData);
  return response.data;
};

/**
 * [GET] 특정 이벤트가 적용된 상품들의 정보를 조회합니다.
 */
export const getEventItems = async (storeId: number, eventId: number): Promise<ItemResponse[]> => {
  // Swagger 명세상 경로가 `/stores/{storeId}/{eventId}/items` 이므로 그대로 따름
  const response = await apiV1Client.get(`/stores/${storeId}/${eventId}/items`);
  return response.data;
};