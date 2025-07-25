import { apiV1Client } from './index'; // v1 API 클라이언트 import

/**
 * 페이징 요청을 위한 타입
 */
export interface Pageable {
  page?: number;
  size?: number;
  sort?: string[]; // 예: ["createdAt,desc"]
}

/**
 * Spring의 Page 객체 응답을 위한 제네릭 타입
 */
export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  [key: string]: any;
}

/**
 * 댓글 생성 요청 시 Body
 */
export interface CommentCreateRequest {
  content: string;
  parentCommentId?: number; // 대댓글이 아닐 경우 null 또는 생략
}

/**
 * 댓글 수정 요청 시 Body
 */
export interface CommentUpdateRequest {
  content: string;
}

/**
 * 댓글 정보 공통 응답 타입
 */
export interface CommentResponse {
  id: number;
  content: string;
  memberId: number;
  username: string;
  postId: number;
  parentCommentId: number | null;
  createdAt: string; // ISO 8601 형식의 날짜 문자열
}


/**
 * [GET] 특정 게시글의 댓글을 페이징하여 조회합니다.
 * @param postId - 게시글 ID
 * @param pageable - 페이징 정보
 */
export const getCommentsByPost = async (
  postId: number,
  pageable: Pageable
): Promise<Page<CommentResponse>> => {
  const response = await apiV1Client.get<Page<CommentResponse>>(
    `/posts/${postId}/comments`,
    {
      params: pageable,
    }
  );
  return response.data;
};

/**
 * [POST] 특정 게시글에 댓글을 생성합니다.
 * @param postId - 댓글을 작성할 게시글 ID
 * @param request - 댓글 내용
 */
export const createComment = async (
  postId: number,
  request: CommentCreateRequest
): Promise<CommentResponse> => {
  const response = await apiV1Client.post<CommentResponse>(
    `/posts/${postId}/comments`,
    request
  );
  return response.data;
};

/**
 * [PATCH] 특정 댓글을 수정합니다.
 * @param commentId - 수정할 댓글 ID
 * @param request - 수정할 내용
 */
export const updateComment = async (
  commentId: number,
  request: CommentUpdateRequest
): Promise<CommentResponse> => {
  const response = await apiV1Client.patch<CommentResponse>(
    `/comments/${commentId}`,
    request
  );
  return response.data;
};

/**
 * [DELETE] 특정 댓글을 삭제합니다.
 * @param commentId - 삭제할 댓글 ID
 */
export const deleteComment = async (commentId: number): Promise<void> => {
  await apiV1Client.delete(`/comments/${commentId}`);
};