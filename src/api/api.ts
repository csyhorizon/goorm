/* eslint-disable */
/* tslint:disable */
// @ts-nocheck
/*
 * ---------------------------------------------------------------
 * ## THIS FILE WAS GENERATED VIA SWAGGER-TYPESCRIPT-API        ##
 * ##                                                           ##
 * ## AUTHOR: acacode                                           ##
 * ## SOURCE: https://github.com/acacode/swagger-typescript-api ##
 * ---------------------------------------------------------------
 */

export interface PostCreateRequest {
  content?: string;
  location?: string;
  mediaUrls?: string[];
}

export interface PostResponse {
  /** @format int64 */
  postId?: number;
  content?: string;
  location?: string;
  mediaUrls?: string[];
  /** @format int64 */
  memberId?: number;
  username?: string;
}

export interface SuccessResponsePostResponse {
  /** @format int32 */
  status?: number;
  message?: string;
  data?: PostResponse;
}

export interface PostLikeResponse {
  /** @format int64 */
  postId?: number;
  liked?: boolean;
  /** @format int32 */
  likeCount?: number;
}

export interface CommentCreateRequest {
  /** @format int64 */
  parentCommentId?: number;
  content?: string;
}

export interface CommentResponse {
  /** @format int64 */
  commentId?: number;
  /** @format int64 */
  userId?: number;
  userName?: string;
  content?: string;
  /** @format int32 */
  likeCount?: number;
  /** @format date-time */
  createdAt?: string;
  /** @format date-time */
  modifiedAt?: string;
  /** @format int64 */
  parentCommentId?: number;
  /** @format int64 */
  postId?: number;
}

export interface SuccessResponseCommentResponse {
  /** @format int32 */
  status?: number;
  message?: string;
  data?: CommentResponse;
}

export interface CommentLikeResponse {
  /** @format int64 */
  commentId?: number;
  liked?: boolean;
  /** @format int32 */
  likeCount?: number;
}

export interface ChatRoomCreateRequest {
  userIds?: number[];
}

export interface ChatRoomCreateResponse {
  /** @format int64 */
  chatRoomId?: number;
  members?: MemberDto[];
}

export interface MemberDto {
  /** @format int64 */
  userId?: number;
  username?: string;
}

export interface ChatMessageRequeset {
  message?: string;
}

export interface ChatMessageResponse {
  /** @format int64 */
  chatMessageId?: number;
  /** @format int64 */
  senderId?: number;
  message?: string;
  /** @format date-time */
  createdAt?: string;
  isRead?: boolean;
}

export interface BookmarkRequest {
  /** @format int64 */
  postId?: number;
}

export interface SuccessResponseBoolean {
  /** @format int32 */
  status?: number;
  message?: string;
  data?: boolean;
}

export interface SigninRequest {
  username?: string;
  password?: string;
}

export interface Cookie {
  name?: string;
  value?: string;
  attributes?: Record<string, string>;
  path?: string;
  /** @deprecated */
  comment?: string;
  /**
   * @deprecated
   * @format int32
   */
  version?: number;
  /** @format int32 */
  maxAge?: number;
  secure?: boolean;
  httpOnly?: boolean;
  domain?: string;
}

export interface SignupRequest {
  username: string;
  password: string;
  confirmPassword: string;
}

export interface UserSummaryResponse {
  /** @format int64 */
  id?: number;
  username?: string;
  role?: string;
  blacklisted?: boolean;
}

export interface PostUpdateRequest {
  content?: string;
  location?: string;
  remainImageUrls?: string[];
  newImageUrls?: string[];
}

export interface CommentUpdateRequest {
  content?: string;
}

export interface ProfileUpdateRequestDto {
  /**
   * @minLength 0
   * @maxLength 255
   */
  bio?: string;
  /**
   * @minLength 0
   * @maxLength 255
   */
  profileImageUrl?: string;
}

export interface ProfileResponseDto {
  /** @format int64 */
  id?: number;
  /**
   * @minLength 0
   * @maxLength 255
   */
  bio?: string;
  /**
   * @minLength 0
   * @maxLength 255
   */
  profileImageUrl?: string;
}

export interface CommentListResponse {
  /** @format int64 */
  commentId?: number;
  /** @format int64 */
  userId?: number;
  userName?: string;
  content?: string;
  /** @format int32 */
  likeCount?: number;
  /** @format date-time */
  createdAt?: string;
  /** @format int64 */
  parentCommentId?: number;
}

export interface LikeUserDto {
  /** @format int64 */
  postId?: number;
  /** @format int64 */
  userId?: number;
  username?: string;
}

export interface PostDetailResponse {
  /** @format int64 */
  postId?: number;
  content?: string;
  location?: string;
  mediaUrls?: string[];
  /** @format int64 */
  memberId?: number;
  username?: string;
  /** @format int32 */
  likeCount?: number;
  likeUsers?: LikeUserDto[];
  likedByMe?: boolean;
  comments?: CommentListResponse[];
}

export interface PageUserPostResponse {
  /** @format int64 */
  totalElements?: number;
  /** @format int32 */
  totalPages?: number;
  /** @format int32 */
  size?: number;
  content?: UserPostResponse[];
  /** @format int32 */
  number?: number;
  sort?: SortObject;
  first?: boolean;
  last?: boolean;
  /** @format int32 */
  numberOfElements?: number;
  pageable?: PageableObject;
  empty?: boolean;
}

export interface PageableObject {
  /** @format int64 */
  offset?: number;
  sort?: SortObject;
  /** @format int32 */
  pageSize?: number;
  paged?: boolean;
  /** @format int32 */
  pageNumber?: number;
  unpaged?: boolean;
}

export interface SortObject {
  empty?: boolean;
  unsorted?: boolean;
  sorted?: boolean;
}

export interface UserPostResponse {
  /** @format int64 */
  postId?: number;
  representativeImageUrl?: string;
}

export interface HomePostResponse {
  /** @format int64 */
  postId?: number;
  content?: string;
  location?: string;
  mediaUrls?: string[];
  /** @format int64 */
  memberId?: number;
  username?: string;
  /** @format int32 */
  likeCount?: number;
  likeUsers?: LikeUserDto[];
  likedByMe?: boolean;
  comments?: CommentResponse[];
}

export interface ChatRoomList {
  /** @format int64 */
  chatroomId?: number;
  type?: "DM" | "GROUP";
  /** @format date-time */
  createdAt?: string;
  lastMessage?: string;
  /** @format date-time */
  lastMessageAt?: string;
  members?: MemberDto[];
}

export interface ChatMessageList {
  /** @format int64 */
  messageId?: number;
  /** @format int64 */
  senderId?: number;
  senderName?: string;
  message?: string;
  /** @format date-time */
  createdAt?: string;
  isRead?: boolean;
}

export interface BookmarkPostResponse {
  /** @format int64 */
  postId?: number;
  representativeImageUrl?: string;
}

export interface SuccessResponseListBookmarkPostResponse {
  /** @format int32 */
  status?: number;
  message?: string;
  data?: BookmarkPostResponse[];
}

export interface SuccessResponseString {
  /** @format int32 */
  status?: number;
  message?: string;
  data?: string;
}

export interface FollowDto {
  /** @format int64 */
  profileId?: number;
  /** @format int64 */
  userId?: number;
  username?: string;
  profileImageUrl?: string;
  bio?: string;
}

export interface UserSearchResultDto {
  /** @format int64 */
  userid?: number;
  username?: string;
}

export interface SearchHistoryDto {
  /** @format int64 */
  userid?: number;
  searchKeyword?: string;
}

export interface ActiveUser {
  /** @format int64 */
  userId?: number;
  username?: string;
  /** @format date-time */
  loginTime?: string;
  ipAddress?: string;
  /** @format date-time */
  lastAccessTime?: string;
}

export interface DashboardResponse {
  /** @format int64 */
  userCount?: number;
  /** @format int64 */
  postCount?: number;
  /** @format int64 */
  commentCount?: number;
  /** @format int64 */
  postLikeCount?: number;
  /** @format int64 */
  commentLikeCount?: number;
  /** @format int64 */
  onlineUserCount?: number;
}

import type {
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
  HeadersDefaults,
  ResponseType,
} from "axios";
import axios from "axios";
import customAxiosInstance from "@/lib/axios"; // customAxiosInstance 임포트

export type QueryParamsType = Record<string | number, any>;

export interface FullRequestParams
  extends Omit<AxiosRequestConfig, "data" | "params" | "url" | "responseType"> {
  /** set parameter to `true` for call `securityWorker` for this request */
  secure?: boolean;
  /** request path */
  path: string;
  /** content type of request body */
  type?: ContentType;
  /** query params */
  query?: QueryParamsType;
  /** format of response (i.e. response.json() -> format: "json") */
  format?: ResponseType;
  /** request body */
  body?: unknown;
}

export type RequestParams = Omit<
  FullRequestParams,
  "body" | "method" | "query" | "path"
>;

export interface ApiConfig<SecurityDataType = unknown>
  extends Omit<AxiosRequestConfig, "data" | "cancelToken"> {
  securityWorker?: (
    securityData: SecurityDataType | null,
  ) => Promise<AxiosRequestConfig | void> | AxiosRequestConfig | void;
  secure?: boolean;
  format?: ResponseType;
}

export enum ContentType {
  Json = "application/json",
  JsonApi = "application/vnd.api+json",
  FormData = "multipart/form-data",
  UrlEncoded = "application/x-www-form-urlencoded",
  Text = "text/plain",
}

export class HttpClient<SecurityDataType = unknown> {
  public instance: AxiosInstance;
  private securityData: SecurityDataType | null = null;
  private securityWorker?: ApiConfig<SecurityDataType>["securityWorker"];
  private secure?: boolean;
  private format?: ResponseType;

  constructor(
    {
      securityWorker,
      secure,
      format,
      ...axiosConfig
    }: ApiConfig<SecurityDataType> = {},
    axiosInstance?: AxiosInstance // AxiosInstance를 선택적으로 주입받도록 추가
  ) {
    this.instance = axiosInstance || axios.create({
      ...axiosConfig,
      baseURL: axiosConfig.baseURL || "/api",
    });
    this.secure = secure;
    this.format = format;
    this.securityWorker = securityWorker;
  }

  public setSecurityData = (data: SecurityDataType | null) => {
    this.securityData = data;
  };

  protected mergeRequestParams(
    params1: AxiosRequestConfig,
    params2?: AxiosRequestConfig,
  ): AxiosRequestConfig {
    const method = params1.method || (params2 && params2.method);

    return {
      ...this.instance.defaults,
      ...params1,
      ...(params2 || {}),
      headers: {
        ...((method &&
          this.instance.defaults.headers[
            method.toLowerCase() as keyof HeadersDefaults
          ]) ||
          {}),
        ...(params1.headers || {}),
        ...((params2 && params2.headers) || {}),
      },
    };
  }

  protected stringifyFormItem(formItem: unknown) {
    if (typeof formItem === "object" && formItem !== null) {
      return JSON.stringify(formItem);
    } else {
      return `${formItem}`;
    }
  }

  protected createFormData(input: Record<string, unknown>): FormData {
    if (input instanceof FormData) {
      return input;
    }
    return Object.keys(input || {}).reduce((formData, key) => {
      const property = input[key];
      const propertyContent: any[] =
        property instanceof Array ? property : [property];

      for (const formItem of propertyContent) {
        const isFileType = formItem instanceof Blob || formItem instanceof File;
        formData.append(
          key,
          isFileType ? formItem : this.stringifyFormItem(formItem),
        );
      }

      return formData;
    }, new FormData());
  }

  public request = async <T = any, _E = any>({
    secure,
    path,
    type,
    query,
    format,
    body,
    ...params
  }: FullRequestParams): Promise<AxiosResponse<T>> => {
    const secureParams =
      ((typeof secure === "boolean" ? secure : this.secure) &&
        this.securityWorker &&
        (await this.securityWorker(this.securityData))) ||
      {};
    const requestParams = this.mergeRequestParams(params, secureParams);
    const responseFormat = format || this.format || undefined;

    if (
      type === ContentType.FormData &&
      body &&
      body !== null &&
      typeof body === "object"
    ) {
      body = this.createFormData(body as Record<string, unknown>);
    }

    if (
      type === ContentType.Text &&
      body &&
      body !== null &&
      typeof body !== "string"
    ) {
      body = JSON.stringify(body);
    }

    return this.instance.request({
      ...requestParams,
      headers: {
        ...(requestParams.headers || {}),
        ...(type ? { "Content-Type": type } : {}),
      },
      params: query,
      responseType: responseFormat,
      data: body,
      url: path,
    });
  };
}

/**
 * @title Uniqram Swagger
 * @version 1.0.0
 * @baseUrl http://localhost:8080
 *
 * 인스타그램 클론 프로젝트 API 명세서
 */
export class Api<
  SecurityDataType extends unknown,
> extends HttpClient<SecurityDataType> {
  constructor(axiosInstance?: AxiosInstance) { // 생성자에 axiosInstance 추가
    super({}, axiosInstance || customAxiosInstance); // customAxiosInstance를 기본값으로 전달
  }
  posts = {
    /**
     * No description
     *
     * @tags post-controller
     * @name CreatePost
     * @request POST:/posts
     * @secure
     */
    createPost: (data: PostCreateRequest, params: RequestParams = {}) =>
      this.request<SuccessResponsePostResponse, any>({
        path: `/posts`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags post-controller
     * @name LikePost
     * @request POST:/posts/{postId}/like
     * @secure
     */
    likePost: (postId: number, params: RequestParams = {}) =>
      this.request<PostLikeResponse, any>({
        path: `/posts/${postId}/like`,
        method: "POST",
        secure: true,
        ...params,
      }),

    /**
     * @description 특정 게시글에 달린 모든 댓글을 조회합니다.
     *
     * @tags post-comment-controller
     * @name GetComments
     * @summary 게시글의 전체 댓글 조회
     * @request GET:/posts/{postId}/comments
     * @secure
     */
    getComments: (postId: number, params: RequestParams = {}) =>
      this.request<CommentListResponse[], any>({
        path: `/posts/${postId}/comments`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * @description 특정 게시글에 새 댓글을 작성합니다.
     *
     * @tags post-comment-controller
     * @name CreateComment
     * @summary 게시글에 댓글 작성
     * @request POST:/posts/{postId}/comments
     * @secure
     */
    createComment: (
      postId: number,
      data: CommentCreateRequest,
      params: RequestParams = {},
    ) =>
      this.request<SuccessResponseCommentResponse, any>({
        path: `/posts/${postId}/comments`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags post-controller
     * @name GetPostDetail
     * @request GET:/posts/{postId}
     * @secure
     */
    getPostDetail: (postId: number, params: RequestParams = {}) =>
      this.request<PostDetailResponse, any>({
        path: `/posts/${postId}`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags post-controller
     * @name DeletePost
     * @request DELETE:/posts/{postId}
     * @secure
     */
    deletePost: (postId: number, params: RequestParams = {}) =>
      this.request<void, any>({
        path: `/posts/${postId}`,
        method: "DELETE",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags post-controller
     * @name UpdatePost
     * @request PATCH:/posts/{postId}
     * @secure
     */
    updatePost: (
      postId: number,
      data: PostUpdateRequest,
      params: RequestParams = {},
    ) =>
      this.request<PostResponse, any>({
        path: `/posts/${postId}`,
        method: "PATCH",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags post-controller
     * @name GetUserPosts
     * @request GET:/posts/profile/{userId}
     * @secure
     */
    getUserPosts: (
      userId: number,
      query?: {
        /**
         * @format int32
         * @default 0
         */
        page?: number;
        /**
         * @format int32
         * @default 10
         */
        size?: number;
      },
      params: RequestParams = {},
    ) =>
      this.request<PageUserPostResponse, any>({
        path: `/posts/profile/${userId}`,
        method: "GET",
        query: query,
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags post-controller
     * @name GetRecommendedPosts
     * @request GET:/posts/home/recommend
     * @secure
     */
    getRecommendedPosts: (params: RequestParams = {}) =>
      this.request<HomePostResponse[], any>({
        path: `/posts/home/recommend`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags post-controller
     * @name GetFollowingRecentPosts
     * @request GET:/posts/home/following
     * @secure
     */
    getFollowingRecentPosts: (params: RequestParams = {}) =>
      this.request<HomePostResponse[], any>({
        path: `/posts/home/following`,
        method: "GET",
        secure: true,
        ...params,
      }),
  };
  comments = {
    /**
     * @description 특정 댓글에 좋아요를 합니다.
     *
     * @tags comment-controller
     * @name LikeComment
     * @summary 댓글 좋아요
     * @request POST:/comments/{commentId}/like
     * @secure
     */
    likeComment: (commentId: number, params: RequestParams = {}) =>
      this.request<CommentLikeResponse, CommentLikeResponse>({
        path: `/comments/${commentId}/like`,
        method: "POST",
        secure: true,
        ...params,
      }),

    /**
     * @description 특정 댓글을 삭제합니다.
     *
     * @tags comment-controller
     * @name DeleteComment
     * @summary 댓글 삭제
     * @request DELETE:/comments/{commentId}
     * @secure
     */
    deleteComment: (commentId: number, params: RequestParams = {}) =>
      this.request<void, void>({
        path: `/comments/${commentId}`,
        method: "DELETE",
        secure: true,
        ...params,
      }),

    /**
     * @description 특정 댓글을 수정합니다.
     *
     * @tags comment-controller
     * @name UpdateComment
     * @summary 댓글 수정
     * @request PATCH:/comments/{commentId}
     * @secure
     */
    updateComment: (
      commentId: number,
      data: CommentUpdateRequest,
      params: RequestParams = {},
    ) =>
      this.request<CommentResponse, CommentResponse>({
        path: `/comments/${commentId}`,
        method: "PATCH",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),
  };
  chatrooms = {
    /**
     * No description
     *
     * @tags chat-room-controller
     * @name GetMyChatRooms
     * @request GET:/chatrooms
     * @secure
     */
    getMyChatRooms: (params: RequestParams = {}) =>
      this.request<ChatRoomList[], any>({
        path: `/chatrooms`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags chat-room-controller
     * @name CreateChatRoom
     * @request POST:/chatrooms
     * @secure
     */
    createChatRoom: (data: ChatRoomCreateRequest, params: RequestParams = {}) =>
      this.request<ChatRoomCreateResponse, any>({
        path: `/chatrooms`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags chat-room-controller
     * @name GetChatMessages
     * @request GET:/chatrooms/{chatRoomId}/messages
     * @secure
     */
    getChatMessages: (
      chatRoomId: number,
      query?: {
        /**
         * @format int32
         * @default 0
         */
        offset?: number;
        /**
         * @format int32
         * @default 20
         */
        limit?: number;
      },
      params: RequestParams = {},
    ) =>
      this.request<ChatMessageList[], any>({
        path: `/chatrooms/${chatRoomId}/messages`,
        method: "GET",
        query: query,
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags chat-room-controller
     * @name SendMessage
     * @request POST:/chatrooms/{chatRoomId}/messages
     * @secure
     */
    sendMessage: (
      chatRoomId: number,
      data: ChatMessageRequeset,
      params: RequestParams = {},
    ) =>
      this.request<ChatMessageResponse, any>({
        path: `/chatrooms/${chatRoomId}/messages`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),
  };
  bookmarks = {
    /**
     * No description
     *
     * @tags bookmark-controller
     * @name GetMyBookmarks
     * @request GET:/bookmarks
     * @secure
     */
    getMyBookmarks: (params: RequestParams = {}) =>
      this.request<SuccessResponseListBookmarkPostResponse, any>({
        path: `/bookmarks`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags bookmark-controller
     * @name Bookmark
     * @request POST:/bookmarks
     * @secure
     */
    bookmark: (data: BookmarkRequest, params: RequestParams = {}) =>
      this.request<SuccessResponseBoolean, any>({
        path: `/bookmarks`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),
  };
  api = {
    /**
     * No description
     *
     * @tags follow-controller
     * @name CreateFollow
     * @request POST:/api/user/follows/{targetUserId}
     * @secure
     */
    createFollow: (targetUserId: number, params: RequestParams = {}) =>
      this.request<void, any>({
        path: `/api/user/follows/${targetUserId}`,
        method: "POST",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags follow-controller
     * @name Unfollow
     * @request DELETE:/api/user/follows/{targetUserId}
     * @secure
     */
    unfollow: (targetUserId: number, params: RequestParams = {}) =>
      this.request<void, any>({
        path: `/api/user/follows/${targetUserId}`,
        method: "DELETE",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags s-3-controller
     * @name UploadFile
     * @request POST:/api/s3
     * @secure
     */
    uploadFile: (
      data: {
        files: File[];
      },
      params: RequestParams = {},
    ) =>
      this.request<string[], any>({
        path: `/api/s3`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags auth-controller
     * @name Signin
     * @request POST:/api/auth/signin
     * @secure
     */
    signin: (data: SigninRequest, params: RequestParams = {}) =>
      this.request<object, any>({
        path: `/api/auth/signin`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags auth-controller
     * @name RefreshToken
     * @request POST:/api/auth/refresh
     * @secure
     */
    refreshToken: (params: RequestParams = {}) =>
      this.request<object, any>({
        path: `/api/auth/refresh`,
        method: "POST",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags auth-controller
     * @name Logout
     * @request POST:/api/auth/logout
     * @secure
     */
    logout: (params: RequestParams = {}) =>
      this.request<object, any>({
        path: `/api/auth/logout`,
        method: "POST",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags auth-controller
     * @name Signup
     * @request POST:/api/auth/join
     * @secure
     */
    signup: (data: SignupRequest, params: RequestParams = {}) =>
      this.request<string, any>({
        path: `/api/auth/join`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags admin-auth-controller
     * @name SignupAdmin
     * @request POST:/api/admin/signup
     * @secure
     */
    signupAdmin: (data: SignupRequest, params: RequestParams = {}) =>
      this.request<string, any>({
        path: `/api/admin/signup`,
        method: "POST",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags profile-controller
     * @name PatchProfile
     * @request PATCH:/api/users/profiles
     * @secure
     */
    patchProfile: (data: ProfileUpdateRequestDto, params: RequestParams = {}) =>
      this.request<ProfileResponseDto, any>({
        path: `/api/users/profiles`,
        method: "PATCH",
        body: data,
        secure: true,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags global-test-controller
     * @name SuccessTest
     * @request GET:/api/users/test/success
     * @secure
     */
    successTest: (params: RequestParams = {}) =>
      this.request<SuccessResponseString, any>({
        path: `/api/users/test/success`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags global-test-controller
     * @name FailTest
     * @request GET:/api/users/test/fail
     * @secure
     */
    failTest: (params: RequestParams = {}) =>
      this.request<object, any>({
        path: `/api/users/test/fail`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags profile-controller
     * @name GetProfile
     * @request GET:/api/users/profiles/{userId}
     * @secure
     */
    getProfile: (userId: number, params: RequestParams = {}) =>
      this.request<ProfileResponseDto, any>({
        path: `/api/users/profiles/${userId}`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags follow-controller
     * @name GetFollowings
     * @request GET:/api/user/users/{userId}/followings
     * @secure
     */
    getFollowings: (userId: number, params: RequestParams = {}) =>
      this.request<FollowDto[], any>({
        path: `/api/user/users/${userId}/followings`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags follow-controller
     * @name GetFollowers
     * @request GET:/api/user/users/{userId}/followers
     * @secure
     */
    getFollowers: (userId: number, params: RequestParams = {}) =>
      this.request<FollowDto[], any>({
        path: `/api/user/users/${userId}/followers`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags role-test-controller
     * @name UserOnly
     * @request GET:/api/user/test
     * @secure
     */
    userOnly: (params: RequestParams = {}) =>
      this.request<string, any>({
        path: `/api/user/test`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags search-controller
     * @name SearchResult
     * @request GET:/api/search/{searchKeyword}
     * @secure
     */
    searchResult: (searchKeyword: string, params: RequestParams = {}) =>
      this.request<UserSearchResultDto[], any>({
        path: `/api/search/${searchKeyword}`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags search-controller
     * @name SearchHistory
     * @request GET:/api/search/search-history
     * @secure
     */
    searchHistory: (params: RequestParams = {}) =>
      this.request<SearchHistoryDto, any>({
        path: `/api/search/search-history`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags role-test-controller
     * @name AdminOnly
     * @request GET:/api/admin/test
     * @secure
     */
    adminOnly: (params: RequestParams = {}) =>
      this.request<string, any>({
        path: `/api/admin/test`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags admin-controller
     * @name GetActiveUsers
     * @request GET:/api/admin/active-users
     * @secure
     */
    getActiveUsers: (params: RequestParams = {}) =>
      this.request<ActiveUser[], any>({
        path: `/api/admin/active-users`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags follow-controller
     * @name RemoveFollower
     * @request DELETE:/api/user/followers/{targetUserId}
     * @secure
     */
    removeFollower: (targetUserId: number, params: RequestParams = {}) =>
      this.request<void, any>({
        path: `/api/user/followers/${targetUserId}`,
        method: "DELETE",
        secure: true,
        ...params,
      }),
  };
  admin = {
    /**
     * No description
     *
     * @tags admin-user-controller
     * @name UnblacklistUserById
     * @request POST:/admin/users/unblacklist/{userId}
     * @secure
     */
    unblacklistUserById: (userId: number, params: RequestParams = {}) =>
      this.request<UserSummaryResponse, any>({
        path: `/admin/users/unblacklist/${userId}`,
        method: "POST",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags admin-user-controller
     * @name BlacklistUserById
     * @request POST:/admin/users/blacklist/{userId}
     * @secure
     */
    blacklistUserById: (userId: number, params: RequestParams = {}) =>
      this.request<UserSummaryResponse, any>({
        path: `/admin/users/blacklist/${userId}`,
        method: "POST",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags admin-user-controller
     * @name GetAllUsers
     * @request GET:/admin/users
     * @secure
     */
    getAllUsers: (params: RequestParams = {}) =>
      this.request<UserSummaryResponse[], any>({
        path: `/admin/users`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags admin-user-controller
     * @name GetOnlineUsers
     * @request GET:/admin/users/online
     * @secure
     */
    getOnlineUsers: (params: RequestParams = {}) =>
      this.request<UserSummaryResponse[], any>({
        path: `/admin/users/online`,
        method: "GET",
        secure: true,
        ...params,
      }),

    /**
     * No description
     *
     * @tags admin-dashboard-controller
     * @name GetDashboardStats
     * @request GET:/admin/dashboard
     * @secure
     */
    getDashboardStats: (params: RequestParams = {}) =>
      this.request<DashboardResponse, any>({
        path: `/admin/dashboard`,
        method: "GET",
        secure: true,
        ...params,
      }),
  };
}
