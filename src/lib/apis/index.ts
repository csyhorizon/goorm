import axios from 'axios';
import { refreshToken } from './auth.api';

const API_BASE_URL = process.env.NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL + '/api';
const baseUrl = process.env.NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL;
const API_BASE_HARD_URL = (baseUrl && baseUrl.trim()) || '/be/api';

/**
 * 인증 관련 API 클라이언트 (ex: /api/auth/signin)
 * - baseURL: .../api
 */
export const apiAuthClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

/**
 * 버전(v1)이 명시된 API 클라이언트 (ex: /api/v1/stores)
 * - baseURL: .../api/v1
 */
export const apiV1Client = axios.create({
  baseURL: API_BASE_HARD_URL + '/v1',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

let isRefreshing = false;
let refreshSubscribers: Array<(token?: string) => void> = [];

function onRefreshed(token?: string) {
  refreshSubscribers.forEach(cb => cb(token));
  refreshSubscribers = [];
}

/**
 * 요청 인터셉터 (v1 클라이언트에만 적용)
 * - 토큰이 필요한 모든 v1 API 요청에 자동으로 Authorization 헤더를 추가합니다.
 */
apiV1Client.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // 401(Unauthorized) 또는 419(Authentication Timeout) 에러 처리
    if ((error.response?.status === 401 || error.response?.status === 419) && !originalRequest._retry) {
      // 토큰 갱신 중인 다른 요청이 있다면 대기
      if (isRefreshing) {
        return new Promise(resolve => {
          refreshSubscribers.push(() => resolve(apiV1Client(originalRequest)));
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      try {
        // refreshToken() 함수를 호출하여 새로운 토큰 발급 시도
        const data = await refreshToken();

        // 갱신 성공 시 대기 중인 요청 재시도
        onRefreshed(data.accessToken);
        return apiV1Client(originalRequest);
      } catch (refreshError) {
        // 토큰 갱신 실패 시 로그인 페이지로 리디렉션
        console.error("토큰 갱신 실패: ", refreshError);
        window.location.href = '/auth/login';
        
        // 리디렉션 후에는 더 이상 에러를 전파하지 않음
        return Promise.reject(refreshError);
      } finally {
        isRefreshing = false;
      }
    }

    // 다른 모든 에러는 그대로 반환
    return Promise.reject(error);
  }
);