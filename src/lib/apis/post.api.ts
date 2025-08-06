import { apiV1Client } from './index';

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
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

/**
 * 게시글 생성 요청 시 JSON 데이터 부분
 */
export interface PostCreateRequest {
  title: string;
  content: string;
  location: string;
  storeId: number;
}

/**
 * 게시글 수정 요청 시 JSON 데이터 부분
 */
export interface PostUpdateRequest {
  title?: string;
  content?: string;
  location?: string;
}

/**
 * 게시글 정보 공통 응답 타입
 */
export interface PostResponse {
  id: number;
  title: string;
  content: string;
  location: string;
  memberId: number;
  memberName: string;
  mediaUrls: string[];
  storeId: number;
  storeName: string;
}



/**
 * [POST] 이미지를 포함한 게시글을 생성합니다.
 * @param request - 게시글 내용 (JSON)
 * @param images - 업로드할 이미지 파일 목록
 */
export const createPost = async (request: PostCreateRequest, images: File[]): Promise<PostResponse> => {
  const formData = new FormData();

  // 1. JSON 데이터를 Blob으로 변환하여 FormData에 추가
  const jsonBlob = new Blob([JSON.stringify(request)], { type: 'application/json' });
  formData.append('postCreateRequest', jsonBlob);

  // 2. 이미지 파일들을 FormData에 추가
  images.forEach(image => {
    formData.append('images', image);
  });

  const response = await apiV1Client.post<PostResponse>('/posts', formData, {
    headers: {
      'Content-Type': 'multipart/form-data', // FormData 사용 시 헤더 명시
    },
  });
  return response.data;
};



/**
 * [PATCH] 게시글을 수정합니다.
 * @param storeId - 가게 ID
 * @param postId - 게시글 ID
 * @param request - 수정할 게시글 내용 (JSON)
 * @param images - 새로 추가할 이미지 파일 목록 (선택 사항)
 */
export const updatePost = async (
  storeId: number,
  postId: number,
  request: PostUpdateRequest,
  images?: File[]
): Promise<PostResponse> => {
  const formData = new FormData();
  
  const jsonBlob = new Blob([JSON.stringify(request)], { type: 'application/json' });
  formData.append('postUpdateRequest', jsonBlob);

  if (images) {
    images.forEach(image => {
      formData.append('images', image);
    });
  }

  const response = await apiV1Client.patch<PostResponse>(`/posts/${storeId}/${postId}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.data;
};


/**
 * [GET] 전체 게시글을 페이징하여 조회합니다.
 */
export const getAllPosts = async (pageable: Pageable): Promise<Page<PostResponse>> => {
  const response = await apiV1Client.get<Page<PostResponse>>('/posts', {
    params: pageable, // { page: 0, size: 10 } 객체를 쿼리 파라미터로 자동 변환
  });
  return response.data;
};


/**
 * [GET] 특정 가게의 모든 게시글을 페이징하여 조회합니다.
 */
export const getPostsByStore = async (storeId: number, pageable: Pageable): Promise<Page<PostResponse>> => {
  const response = await apiV1Client.get<Page<PostResponse>>(`/posts/${storeId}`, {
    params: pageable, // { page: 0, size: 10 } 객체를 쿼리 파라미터로 자동 변환
  });
  return response.data;
};




/**
 * [GET] 특정 게시글의 상세 정보를 조회합니다.
 */
export const getPostDetail = async (storeId: number, postId: number): Promise<PostResponse> => {
  const response = await apiV1Client.get<PostResponse>(`/posts/${storeId}/${postId}`);
  return response.data;
};




/**
 * [DELETE] 특정 게시글을 삭제합니다.
 */
export const deletePost = async (storeId: number, postId: number): Promise<void> => {
  await apiV1Client.delete(`/posts/${storeId}/${postId}`);
};