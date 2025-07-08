
import React, { useEffect, useState } from 'react';
import { StoryCarousel } from './StoryCarousel';
import { FeedPost } from './FeedPost';
import { Api, HomePostResponse } from '@/api/api'; // Api 클래스와 HomePostResponse 타입 임포트

export const MainFeed = () => {
  const [posts, setPosts] = useState<HomePostResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const api = new Api(); // Api 클래스의 인스턴스 생성

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        setLoading(true);
        // getRecommendedPosts API 호출
        const response = await api.posts.getRecommendedPosts();
        setPosts(response.data); // 응답 데이터로 게시물 상태 업데이트
      } catch (err) {
        console.error('Failed to fetch posts:', err);
        setError('게시물을 불러오는 데 실패했습니다.');
      } finally {
        setLoading(false);
      }
    };

    fetchPosts();
  }, []);

  if (loading) {
    return <div className="flex-1 max-w-2xl mx-auto text-center py-8">게시물을 불러오는 중...</div>;
  }

  if (error) {
    return <div className="flex-1 max-w-2xl mx-auto text-center py-8 text-red-500">{error}</div>;
  }

  return (
    <div className="flex-1 max-w-2xl mx-auto">
      {/* Story Carousel */}
      <div className="pt-6 pb-4">
        <StoryCarousel />
      </div>

      {/* Feed Posts */}
      <div className="space-y-6">
        {posts.length > 0 ? (
          posts.map((post) => (
            <FeedPost key={post.postId} post={post} />
          ))
        ) : (
          <div className="text-center py-8 text-instagram-muted">게시물이 없습니다.</div>
        )}
      </div>
    </div>
  );
};

