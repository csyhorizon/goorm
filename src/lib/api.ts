// ========================================
// 🌐 api.ts - API 통신 설정 및 엔드포인트 정의
// ========================================
// 이 파일은 백엔드 서버와의 통신을 담당하는 API 설정입니다.
// React Query + Redux Toolkit Query를 사용하여 API 호출, 캐싱, 로딩 상태를 관리합니다.

// 📦 필요한 라이브러리 가져오기
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

// 📝 API 응답 데이터의 타입 정의 (TypeScript)
// 이렇게 타입을 정의하면 코드 작성 시 자동완성과 오류 검사를 받을 수 있습니다

// 👤 사용자 정보 타입
export interface User {
  id: number;           // 사용자 고유 ID
  username: string;      // 사용자명
  email: string;         // 이메일
  profileImage?: string; // 프로필 이미지 URL (선택사항)
}

// 📄 게시물 타입
export interface Post {
  id: number;           // 게시물 고유 ID
  userId: number;       // 작성자 ID
  content: string;      // 게시물 내용
  images: string[];     // 이미지 URL 배열
  createdAt: string;    // 생성 시간
  updatedAt: string;    // 수정 시간
  user: User;           // 작성자 정보
  likeCount: number;    // 좋아요 수
  commentCount: number; // 댓글 수
  isLiked: boolean;     // 내가 좋아요 눌렀는지 여부
}

// 💬 댓글 타입
export interface Comment {
  id: number;        // 댓글 고유 ID
  postId: number;    // 게시물 ID
  userId: number;    // 작성자 ID
  content: string;   // 댓글 내용
  createdAt: string; // 생성 시간
  user: User;        // 작성자 정보
}

// 🔐 인증 응답 타입
export interface AuthResponse {
  token: string; // JWT 토큰
  user: User;    // 사용자 정보
}

// 🔧 커스텀 baseQuery - 백엔드 연결 실패 시 오류 무시
// 백엔드 서버가 없어도 프론트엔드가 정상 작동하도록 하는 설정
const customBaseQuery = async (args: any, api: any, extraOptions: any) => {
  try {
    // 기본 fetch 설정
    const result = await fetchBaseQuery({
      baseUrl: 'http://localhost:8080/api', // 백엔드 서버 주소
      prepareHeaders: (headers) => {
        // 로컬 스토리지에서 토큰을 가져와서 요청 헤더에 추가
        const token = localStorage.getItem('token');
        if (token) {
          headers.set('authorization', `Bearer ${token}`);
        }
        return headers;
      },
    })(args, api, extraOptions);
    return result;
  } catch {
    // 백엔드 연결 실패 시 오류 무시하고 빈 데이터 반환
    console.log('Backend not available, using dummy data');
    return { data: null };
  }
};

// 🏗️ API 서비스 생성
export const apiService = createApi({
  reducerPath: 'api', // Redux 스토어에서 사용할 경로
  baseQuery: customBaseQuery, // 위에서 정의한 커스텀 baseQuery 사용
  tagTypes: ['Post', 'User', 'Comment'], // 캐시 태그 타입들
  endpoints: (builder) => ({
    // 🔐 인증 관련 API 엔드포인트들
    
    // 로그인
    login: builder.mutation<AuthResponse, { email: string; password: string }>({
      query: (credentials) => ({
        url: '/auth/login',
        method: 'POST',
        body: credentials, // 이메일과 비밀번호 전송
      }),
    }),
    
    // 회원가입
    register: builder.mutation<AuthResponse, { username: string; email: string; password: string }>({
      query: (userData) => ({
        url: '/auth/register',
        method: 'POST',
        body: userData, // 사용자명, 이메일, 비밀번호 전송
      }),
    }),
    
    // 내 프로필 정보 가져오기
    getProfile: builder.query<User, void>({
      query: () => '/auth/profile',
      providesTags: ['User'], // User 태그로 캐시 관리
    }),

    // 📄 포스트 관련 API 엔드포인트들
    
    // 게시물 목록 가져오기
    getPosts: builder.query<Post[], { page?: number; limit?: number }>({
      query: ({ page = 1, limit = 10 }) => `/posts?page=${page}&limit=${limit}`,
      providesTags: ['Post'], // Post 태그로 캐시 관리
    }),
    
    // 특정 게시물 가져오기
    getPost: builder.query<Post, number>({
      query: (id) => `/posts/${id}`,
      providesTags: (result, error, id) => [{ type: 'Post', id }], // 특정 게시물 캐시
    }),
    
    // 새 게시물 작성
    createPost: builder.mutation<Post, { content: string; images?: File[] }>({
      query: (postData) => ({
        url: '/posts',
        method: 'POST',
        body: postData, // 게시물 내용과 이미지 전송
      }),
      invalidatesTags: ['Post'], // 게시물 생성 후 Post 캐시 무효화
    }),
    
    // 게시물 좋아요
    likePost: builder.mutation<void, number>({
      query: (postId) => ({
        url: `/posts/${postId}/like`,
        method: 'POST',
      }),
      invalidatesTags: (result, error, id) => [{ type: 'Post', id }], // 해당 게시물 캐시 무효화
    }),

    // 💬 댓글 관련 API 엔드포인트들
    
    // 게시물의 댓글 목록 가져오기
    getComments: builder.query<Comment[], number>({
      query: (postId) => `/posts/${postId}/comments`,
      providesTags: (result, error, postId) => [{ type: 'Comment', id: postId }], // 특정 게시물 댓글 캐시
    }),
    
    // 댓글 작성
    createComment: builder.mutation<Comment, { postId: number; content: string }>({
      query: ({ postId, content }) => ({
        url: `/posts/${postId}/comments`,
        method: 'POST',
        body: { content }, // 댓글 내용 전송
      }),
      invalidatesTags: (result, error, { postId }) => [{ type: 'Comment', id: postId }], // 해당 게시물 댓글 캐시 무효화
    }),

    // 👤 사용자 관련 API 엔드포인트들
    
    // 특정 사용자 프로필 가져오기
    getUserProfile: builder.query<User, number>({
      query: (userId) => `/users/${userId}`,
      providesTags: (result, error, id) => [{ type: 'User', id }], // 특정 사용자 캐시
    }),
    
    // 내 프로필 정보 수정
    updateProfile: builder.mutation<User, { username?: string; profileImage?: File }>({
      query: (profileData) => ({
        url: '/users/profile',
        method: 'PUT',
        body: profileData, // 수정할 정보 전송
      }),
      invalidatesTags: ['User'], // 프로필 수정 후 User 캐시 무효화
    }),
  }),
});

// 🎣 React 훅들 내보내기
// 이 훅들을 컴포넌트에서 사용하여 API 호출, 로딩 상태, 에러 상태를 쉽게 관리할 수 있습니다
export const {
  // 인증 관련 훅들
  useLoginMutation,        // 로그인 훅
  useRegisterMutation,     // 회원가입 훅
  useGetProfileQuery,      // 내 프로필 가져오기 훅
  
  // 게시물 관련 훅들
  useGetPostsQuery,        // 게시물 목록 가져오기 훅
  useGetPostQuery,         // 특정 게시물 가져오기 훅
  useCreatePostMutation,   // 게시물 작성 훅
  useLikePostMutation,     // 게시물 좋아요 훅
  
  // 댓글 관련 훅들
  useGetCommentsQuery,     // 댓글 목록 가져오기 훅
  useCreateCommentMutation, // 댓글 작성 훅
  
  // 사용자 관련 훅들
  useGetUserProfileQuery,  // 사용자 프로필 가져오기 훅
  useUpdateProfileMutation, // 프로필 수정 훅
} = apiService; 