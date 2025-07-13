import React from 'react';
import { HomePostResponse, useGetFollowingRecentPosts } from '@/lib/postApi';
import MainFeed from '@/components/home/MainFeed';
import { RightPanel } from '@/components/home/RightPanel';

const HomePage: React.FC = () => {
  const { data: posts = [], isLoading, error } = useGetFollowingRecentPosts();
  const displayPosts = posts;

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-gray-900"></div>
        <p className="text-white mt-4">게시물을 불러오는 중...</p>
      </div>
    );
  }

  return (
<div className="bg-black w-full flex justify-center">
  <MainFeed posts={displayPosts} />
  <RightPanel />
</div>
  );
};

export default HomePage;