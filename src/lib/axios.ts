import axios from 'axios';

// axios 인스턴스 생성
const api = axios.create({
  baseURL: '', // Swagger API가 전체 path (/api/auth/join)를 포함하므로 baseURL은 빈 문자열
  timeout: 10000,
  // Content-Type 헤더 제거 - Swagger API에서 명시적으로 설정하도록 함
});

// 요청 인터셉터
api.interceptors.request.use(
  (config) => {
    // 토큰이 있으면 헤더에 추가
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // 401 에러 시 토큰 제거 및 로그인 페이지로 리다이렉트
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
