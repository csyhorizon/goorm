
import React from 'react';
import { useGetPostsQuery } from '@/lib/api';
import MainFeed from '@/components/home/MainFeed';
import { Sidebar } from '@/components/Sidebar';
import { RightPanel } from '@/components/home/RightPanel';

const HomePage: React.FC = () => {
  const { data: posts, isLoading, error } = useGetPostsQuery({ page: 1, limit: 10 });

  // 백엔드가 없을 때를 위한 더미 데이터
  const dummyPosts = [
    {
      id: 1,
      userId: 1,
      content: "안녕하세요! 첫 번째 게시물입니다. 🎉",
      images: ["https://via.placeholder.com/400x400/FF6B6B/FFFFFF?text=Post+1"],
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
      user: {
        id: 1,
        username: "user1",
        email: "user1@example.com",
        profileImage: "https://via.placeholder.com/50x50/4ECDC4/FFFFFF?text=U1"
      },
      likeCount: 5,
      commentCount: 2,
      isLiked: false
    },
    {
      id: 2,
      userId: 2,
      content: "오늘 날씨가 정말 좋네요! ☀️",
      images: ["https://via.placeholder.com/400x400/45B7D1/FFFFFF?text=Post+2"],
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
      user: {
        id: 2,
        username: "user2",
        email: "user2@example.com",
        profileImage: "https://via.placeholder.com/50x50/96CEB4/FFFFFF?text=U2"
      },
      likeCount: 12,
      commentCount: 5,
      isLiked: true
    }
  ];

  // 백엔드 연결 실패 시 더미 데이터 사용
  const displayPosts = error ? dummyPosts : (posts || []);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-gray-900"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 flex">
      {/* Left Sidebar */}
      <Sidebar />
      
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
