import axios from 'axios';
import { getToken, removeToken } from './auth';

const instance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api', // 환경 변수에서 API 기본 URL을 가져오거나 기본값 사용
  timeout: 10000, // 요청 타임아웃 10초
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터: 모든 요청에 JWT 토큰 추가
instance.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터: 401 Unauthorized 에러 처리
instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // 401 Unauthorized 에러 발생 시 JWT 제거 및 로그인 페이지로 리디렉션
      removeToken();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default instance;
