import axios from 'axios';

// axios 인스턴스 생성
const api = axios.create({
  baseURL: 'http://localhost:8080', // 🎯 백엔드 서버로 요청 전송
  timeout: 10000,
  withCredentials: true, // 🔒 HTTP-only 쿠키를 자동으로 포함하기 위해 필수
  // Content-Type 헤더 제거 - Swagger API에서 명시적으로 설정하도록 함
});

// 요청 인터셉터
api.interceptors.request.use(
  (config) => {
    // 토큰이 있으면 헤더에 추가 (올바른 키 사용)
    const token = localStorage.getItem('jwt_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      console.log('🔑 API 요청에 토큰 포함됨');
    } else {
      console.log('⚠️ 토큰이 없어서 API 요청에 Authorization 헤더 없음');
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
      localStorage.removeItem('jwt_token');
      console.log('🚫 401 에러로 토큰 제거됨');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
