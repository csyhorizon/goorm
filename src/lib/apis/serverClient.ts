import axios, { type AxiosInstance } from 'axios';
import { ReadonlyRequestCookies } from 'next/dist/server/web/spec-extension/adapters/request-cookies';

const API_BASE_URL = process.env.NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL;

/**
 * Next.js 서버 환경(API Routes, 서버 컴포넌트)에서 사용할 API 클라이언트를 생성하는 팩토리 함수입니다.
 * * @param cookieStore - `next/headers`의 `cookies()`를 통해 얻은 쿠키 저장소
 * @returns Cookie 헤더가 자동으로 포함된 Axios 인스턴스
 */
export const createServerApi = (cookieStore: ReadonlyRequestCookies): AxiosInstance => {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Content-Type': 'application/json',
    },
    // withCredentials는 서버 환경에서 의미가 없으므로 제거해도 무방합니다.
  });

  // 2. 요청 인터셉터를 사용하여 모든 요청에 Cookie 헤더를 동적으로 추가합니다.
  instance.interceptors.request.use(
    (config) => {
      // 이 클라이언트로 보내는 모든 요청은 여기서 쿠키 헤더를 설정합니다.
      const accessToken = cookieStore.get('accessToken')?.value;
      const refreshToken = cookieStore.get('refreshToken')?.value;

      if (accessToken && refreshToken) {
        config.headers['Cookie'] = `accessToken=${accessToken}; refreshToken=${refreshToken}`;
      }
      
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  return instance;
};