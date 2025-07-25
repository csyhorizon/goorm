
const SPRING_BOOT_API_BASE_URL = process.env.NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL + '/api';

interface LoginRequest {
  email: string;
  password: string;
}

interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  username: string;
  email: string;
  roles: string[];
}

interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
}

interface RegisterResponse {
  message: string;
}

/**
 * Spring Boot 백엔드의 로그인 API를 호출하는 함수
 * Next.js API 라우트에서 사용
 *
 * @param {LoginRequest} credentials - 사용자 로그인 정보 (이메일, 비밀번호)
 * @returns {Promise<LoginResponse>} - 로그인 성공 시 백엔드의 응답 데이터
 * @throws {Error} - 로그인 실패 시 에러 발생
 */
export async function springBootLogin(credentials: LoginRequest): Promise<LoginResponse> {
  if (!SPRING_BOOT_API_BASE_URL) {
    throw new Error('Spring Boot API base URL is not defined in environment variables.');
  }

  try {
    const response = await fetch(`${SPRING_BOOT_API_BASE_URL}/auth/signin`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || '로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.');
    }

    return response.json();

  } catch (error: unknown) {
    console.error('Spring Boot 로그인 API 호출 중 오류 발생:', error);
    throw new Error(error instanceof Error ? error.message : '네트워크 오류가 발생했습니다. 다시 시도해주세요.');
  }
}

/**
 * Spring Boot 백엔드의 회원가입 API를 호출하는 함수
 * Next.js API 라우트에서 사용
 *
 * @param {RegisterRequest} userData - 사용자 회원가입 정보 (사용자 이름, 이메일, 비밀번호, 비밀번호 확인)
 * @returns {Promise<RegisterResponse>} - 회원가입 성공 시 백엔드의 응답 데이터
 * @throws {Error} - 회원가입 실패 시 에러 발생
 */
export async function springBootRegister(userData: RegisterRequest): Promise<RegisterResponse> {
  if (!SPRING_BOOT_API_BASE_URL) {
    throw new Error('Spring Boot API base URL is not defined in environment variables.');
  }

  try {
    const response = await fetch(`${SPRING_BOOT_API_BASE_URL}/auth/join`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || '회원가입에 실패했습니다. 입력 정보를 확인해주세요.');
    }

    return response.json();

  } catch (error: unknown) {
    console.error('Spring Boot 회원가입 API 호출 중 오류 발생:', error);
    console.error('회원가입 요청 데이터:', userData);
    console.error('API 호출 URL:', `${SPRING_BOOT_API_BASE_URL}/auth/join`);
    throw new Error(error instanceof Error ? error.message : '네트워크 오류가 발생했습니다. 다시 시도해주세요.');
  }
}