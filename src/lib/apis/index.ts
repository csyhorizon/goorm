import axios from 'axios';
import { refreshToken } from './auth.api';

const API_BASE_URL = process.env.NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL + '/api';

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
  baseURL: `${API_BASE_URL}/v1`,
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

    if ((error.response?.status === 401 || error.response?.status === 419) && !originalRequest._retry) {
      if (isRefreshing) {
        return new Promise(resolve => {
          refreshSubscribers.push(() => resolve(apiV1Client(originalRequest)));
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      try {
        const data = await refreshToken();

        onRefreshed(data.accessToken);
        return apiV1Client(originalRequest);
      } catch (refreshError) {
        return Promise.reject(refreshError);
      } finally {
        isRefreshing = false;
      }
    }

    return Promise.reject(error);
  }
);