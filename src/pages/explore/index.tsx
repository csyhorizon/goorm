import React from 'react';
import { useGetPostsQuery } from '@/lib/api';
import { Sidebar } from '@/components/Sidebar';
import { RightPanel } from '@/components/RightPanel';

const ExplorePage: React.FC = () => {
  const { data: posts, isLoading, error } = useGetPostsQuery({ page: 1, limit: 20 });

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-gray-900"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-red-500">탐색 중 오류가 발생했습니다.</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 flex">
      {/* Left Sidebar */}
      <Sidebar />
      
      {/* Main Content */}
      <div className="flex-1 flex justify-center">
        <div className="max-w-6xl w-full p-6">
          <h1 className="text-2xl font-bold mb-6">탐색</h1>
          <div className="grid grid-cols-3 gap-4">
            {posts?.map((post) => (
              <div key={post.id} className="aspect-square bg-gray-200 rounded-lg overflow-hidden">
                {post.images[0] && (
                  <img 
                    src={post.images[0]} 
                    alt="게시물" 
                    className="w-full h-full object-cover"
                  />
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
      
      {/* Right Panel */}
      <RightPanel />
    </div>
  );
};

export default ExplorePage; 