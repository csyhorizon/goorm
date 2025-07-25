import { apiAuthClient } from './index';

// 로그인 요청
export interface LoginRequest {
  email: string;
  password: string;
}

// 로그인 응답
export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  redirectUrl: string;
  message: string;
  user: {
    id: number;
    username: string;
    role: string;
  };
}

// 회원가입 요청
export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
}

// 회원가입 응답
export interface RegisterResponse {
  message: string;
}

// 토큰 갱신 응답
export interface RefreshResponse {
    accessToken: string;
    message: string;
}



/**
 * [POST] 이메일과 비밀번호로 로그인합니다.
 * @param {LoginRequest} credentials - 사용자 로그인 정보
 */
export const login = async (credentials: LoginRequest): Promise<LoginResponse> => {
  const response = await apiAuthClient.post<LoginResponse>('/auth/signin', credentials);
  return response.data;
};

/**
 * [POST] 회원가입을 요청합니다.
 * @param {RegisterRequest} userData - 사용자 회원가입 정보
 */
export const register = async (userData: RegisterRequest): Promise<RegisterResponse> => {
  const response = await apiAuthClient.post<RegisterResponse>('/auth/join', userData);
  return response.data;
};

/**
 * [POST] 쿠키에 담긴 리프레시 토큰으로 액세스 토큰을 갱신합니다.
 */
export const refreshToken = async (): Promise<RefreshResponse> => {
  // 쿠키는 브라우저가 자동으로 보내주므로, 별도의 인자는 필요 없습니다.
  const response = await apiAuthClient.post<RefreshResponse>('/auth/refresh');
  return response.data;
};

/**
 * [POST] 서버에 로그아웃을 요청하여 쿠키를 삭제합니다.
 */
export const logout = async (): Promise<void> => {
  await apiAuthClient.post('/auth/logout');
};