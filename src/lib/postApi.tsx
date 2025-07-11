import axios from 'axios'
import { useQuery } from '@tanstack/react-query';

export interface HomePostResponse {
    postId: number;
    content: string;
    location: string | null;
    mediaUrls: string[];
    memberId: number;
    username: string;
  
    likeCount: number;
    likeUsers: LikeUserDto[];
    likedByMe: boolean;
  
    comments: CommentResponse[];
}

export interface LikeUserDto {
    postId: number;      
    userId: number;
    username: string;
}

export interface CommentResponse {
    commentId: number;
    userId: number;
    userName: string;        
    content: string;
    likeCount: number;
    createdAt: string;
    modifiedAt: string;
    parentCommentId: number | null;
    postId: number;
  }

  export const getFollowingRecentPosts = async (): Promise<HomePostResponse[]> => {
    try {
      const response = await axios.get('/home/following');
      console.log('response.data:', response.data);
      return response.data;
    } catch (error) {
      console.error('🚨 axios error:', error);
      return [];  // 실패 시 빈 배열 반환
    }
  };

export const useGetFollowingRecentPosts = () => {
  return useQuery<HomePostResponse[]>({
    queryKey: ['followingRecentPosts'],
    queryFn: getFollowingRecentPosts,
  });
};
