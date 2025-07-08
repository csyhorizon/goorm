
import React, { useEffect, useState } from 'react';
import { Heart, MessageCircle, Play } from 'lucide-react';
import { Api, UserPostResponse, PageUserPostResponse } from '@/api/api'; // Import Api and relevant types
import { useSelector } from 'react-redux';
import { RootState } from '@/app/store';

interface ProfileGridProps {
  type: 'posts' | 'reels' | 'tagged';
}

export const ProfileGrid: React.FC<ProfileGridProps> = ({ type }) => {
  const [posts, setPosts] = useState<UserPostResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const api = new Api();
  const currentUserId = useSelector((state: RootState) => state.auth.user?.id);

  useEffect(() => {
    if (type === 'posts' && currentUserId) {
      const fetchUserPosts = async () => {
        try {
          setLoading(true);
          // Assuming we want to fetch posts for the current user
          const response = await api.posts.getUserPosts(currentUserId, { page: 0, size: 10 }); // Fetch first 10 posts
          setPosts(response.data.content || []); // Assuming content holds the array of posts
        } catch (err) {
          console.error('Failed to fetch user posts:', err);
          setError('게시물을 불러오는 데 실패했습니다.');
        } finally {
          setLoading(false);
        }
      };
      fetchUserPosts();
    } else if (!currentUserId) {
      setError('사용자 ID를 찾을 수 없습니다.');
    }
  }, [type, currentUserId]);

  if (loading) {
    return (
      <div className="flex flex-col items-center justify-center py-16 text-instagram-muted">
        <h3 className="text-lg font-medium mb-2">게시물을 불러오는 중...</h3>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col items-center justify-center py-16 text-red-500">
        <h3 className="text-lg font-medium mb-2">{error}</h3>
      </div>
    );
  }

  if (type !== 'posts') {
    return (
      <div className="flex flex-col items-center justify-center py-16 text-instagram-muted">
        <div className="text-4xl mb-4">🚧</div>
        <h3 className="text-lg font-medium mb-2">
          {type === 'reels' ? '릴스' : '태그된 게시물'}은(는) 아직 지원되지 않습니다.
        </h3>
        <p className="text-sm">다른 탭을 선택해주세요.</p>
      </div>
    );
  }

  if (posts.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center py-16 text-instagram-muted">
        <div className="text-4xl mb-4">📷</div>
        <h3 className="text-lg font-medium mb-2">아직 게시물이 없습니다</h3>
        <p className="text-sm">첫 번째 게시물을 공유해보세요!</p>
      </div>
    );
  }

  return (
    <div className="grid grid-cols-3 gap-1 pt-4">
      {posts.map((post) => (
        <div key={post.postId} className="aspect-square relative group cursor-pointer">
          <img
            src={post.representativeImageUrl}
            alt={`게시물 ${post.postId}`}
            className="w-full h-full object-cover"
          />

          {/* Hover overlay - Likes and Comments are not directly available in UserPostResponse */}
          <div className="absolute inset-0 bg-black bg-opacity-50 opacity-0 group-hover:opacity-100 transition-opacity duration-200 flex items-center justify-center">
            <div className="flex items-center gap-4 text-white">
              {/* These values are not directly available from UserPostResponse */}
              {/* <div className="flex items-center gap-1">
                <Heart size={20} className="fill-current" />
                <span className="font-semibold">{post.likes}</span>
              </div>
              <div className="flex items-center gap-1">
                <MessageCircle size={20} className="fill-current" />
                <span className="font-semibold">{post.comments}</span>
              </div> */}
              <span className="font-semibold">상세 보기</span>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};
