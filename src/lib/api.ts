import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

// API 응답 타입 정의
export interface User {
  id: number;
  username: string;
  email: string;
  profileImage?: string;
}

export interface Post {
  id: number;
  userId: number;
  content: string;
  images: string[];
  createdAt: string;
  updatedAt: string;
  user: User;
  likeCount: number;
  commentCount: number;
  isLiked: boolean;
}

export interface Comment {
  id: number;
  postId: number;
  userId: number;
  content: string;
  createdAt: string;
  user: User;
}

export interface AuthResponse {
  token: string;
  user: User;
}

// 커스텀 baseQuery - 백엔드 연결 실패 시 오류 무시
const customBaseQuery = async (args: any, api: any, extraOptions: any) => {
  try {
    const result = await fetchBaseQuery({
      baseUrl: 'http://localhost:8080/api',
      prepareHeaders: (headers) => {
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

// API 서비스 생성
export const apiService = createApi({
  reducerPath: 'api',
  baseQuery: customBaseQuery,
  tagTypes: ['Post', 'User', 'Comment'],
  endpoints: (builder) => ({
    // 인증 관련
    login: builder.mutation<AuthResponse, { email: string; password: string }>({
      query: (credentials) => ({
        url: '/auth/login',
        method: 'POST',
        body: credentials,
      }),
    }),
    register: builder.mutation<AuthResponse, { username: string; email: string; password: string }>({
      query: (userData) => ({
        url: '/auth/register',
        method: 'POST',
        body: userData,
      }),
    }),
    getProfile: builder.query<User, void>({
      query: () => '/auth/profile',
      providesTags: ['User'],
    }),

    // 포스트 관련
    getPosts: builder.query<Post[], { page?: number; limit?: number }>({
      query: ({ page = 1, limit = 10 }) => `/posts?page=${page}&limit=${limit}`,
      providesTags: ['Post'],
    }),
    getPost: builder.query<Post, number>({
      query: (id) => `/posts/${id}`,
      providesTags: (result, error, id) => [{ type: 'Post', id }],
    }),
    createPost: builder.mutation<Post, { content: string; images?: File[] }>({
      query: (postData) => ({
        url: '/posts',
        method: 'POST',
        body: postData,
      }),
      invalidatesTags: ['Post'],
    }),
    likePost: builder.mutation<void, number>({
      query: (postId) => ({
        url: `/posts/${postId}/like`,
        method: 'POST',
      }),
      invalidatesTags: (result, error, id) => [{ type: 'Post', id }],
    }),

    // 댓글 관련
    getComments: builder.query<Comment[], number>({
      query: (postId) => `/posts/${postId}/comments`,
      providesTags: (result, error, postId) => [{ type: 'Comment', id: postId }],
    }),
    createComment: builder.mutation<Comment, { postId: number; content: string }>({
      query: ({ postId, content }) => ({
        url: `/posts/${postId}/comments`,
        method: 'POST',
        body: { content },
      }),
      invalidatesTags: (result, error, { postId }) => [{ type: 'Comment', id: postId }],
    }),

    // 사용자 관련
    getUserProfile: builder.query<User, number>({
      query: (userId) => `/users/${userId}`,
      providesTags: (result, error, id) => [{ type: 'User', id }],
    }),
    updateProfile: builder.mutation<User, { username?: string; profileImage?: File }>({
      query: (profileData) => ({
        url: '/users/profile',
        method: 'PUT',
        body: profileData,
      }),
      invalidatesTags: ['User'],
    }),
  }),
});

// 훅 내보내기
export const {
  useLoginMutation,
  useRegisterMutation,
  useGetProfileQuery,
  useGetPostsQuery,
  useGetPostQuery,
  useCreatePostMutation,
  useLikePostMutation,
  useGetCommentsQuery,
  useCreateCommentMutation,
  useGetUserProfileQuery,
  useUpdateProfileMutation,
} = apiService; 