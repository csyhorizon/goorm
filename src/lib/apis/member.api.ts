import { apiV1Client } from './index';

/**
 * 회원 정보 응답 타입
 */
export interface MemberResponse {
  id: number;
  username: string;
  email: string;
  role: 'USER' | 'ADMIN' | 'OWNER'; // 역할은 보통 정해진 값이므로 Union 타입으로 정의
}


/**
 * [GET] 현재 로그인된 사용자의 프로필 정보를 조회합니다.
 * (API 요청 시 헤더에 담긴 토큰을 기반으로 서버에서 사용자를 식별합니다)
 */
export const getMyProfile = async (): Promise<MemberResponse> => {
  const response = await apiV1Client.get<MemberResponse>('/member');
  return response.data;
};