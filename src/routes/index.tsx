import React from 'react';
import MainFeed from '@/components/home/MainFeed';
import { RightPanel } from '@/components/home/RightPanel';
import { useGetFollowingRecentPosts } from '@/lib/postApi';
import { HomePostResponse } from '@/lib/postApi';

const HomePage: React.FC = () => {
  const { data: posts = [], isLoading, error } = useGetFollowingRecentPosts();

  const dummyPosts: HomePostResponse[] = [
    {
      postId: 1,
      memberId: 1,
      username: "user1",
      content: "안녕하세요! 첫 번째 게시물입니다. 🎉",
      location: null,
      mediaUrls: ["https://via.placeholder.com/400x400/FF6B6B/FFFFFF?text=Post+1"],
      likeCount: 5,
      likedByMe: false,
      likeUsers: [],
      comments: [],
    },
    {
      postId: 2,
      memberId: 2,
      username: "user2",
      content: "오늘 날씨가 정말 좋네요! ☀️",
      location: null,
      mediaUrls: ["https://via.placeholder.com/400x400/45B7D1/FFFFFF?text=Post+2"],
      likeCount: 12,
      likedByMe: true,
      likeUsers: [],
      comments: [],
    },
  ];
  

  const displayPosts = error ? dummyPosts : (posts || []);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-gray-900"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-black flex">
      {/* Main Content */}
      <div className="flex-1 flex justify-center">
        <MainFeed posts={displayPosts} />
      </div>

      {/* Right Panel */}
      <RightPanel />
    </div>
  );
};

export default HomePage;
