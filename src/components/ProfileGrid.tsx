import React, { useState } from 'react';
import { PostDetailModal } from '@/components/PostDetailModal';

const S3_BASE_URL =
  import.meta.env.VITE_S3_BASE_URL ||
  'https://uniqrambucket.s3.ap-northeast-2.amazonaws.com/';

interface PostItem {
  postId: number;
  representativeImageUrl: string;
}

interface ProfileGridProps {
  userId: number;
  type?: 'posts' | 'reels' | 'tagged';
  posts: PostItem[];
}

export const ProfileGrid: React.FC<ProfileGridProps> = ({
  type = 'posts',
  posts = [],
}) => {
  const [selectedPostId, setSelectedPostId] = useState<number | null>(null);

  const handleOpenPostDetail = (postId: number) => {
    setSelectedPostId(postId);
  };

  const handleClosePostDetail = () => {
    setSelectedPostId(null);
  };

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
    <div className="relative">
      <div className="grid grid-cols-3 gap-1">
        {posts.map((post) => {
          const imageUrl = post.representativeImageUrl.startsWith('http')
            ? post.representativeImageUrl
            : `${S3_BASE_URL}${post.representativeImageUrl}`;

          return (
            <div
              key={post.postId}
              className="relative w-full pt-[100%] bg-gray-900 overflow-hidden cursor-pointer"
              onClick={() => handleOpenPostDetail(post.postId)}
            >
              <img
                src={imageUrl}
                alt={`Post ${post.postId}`}
                className="absolute inset-0 w-full h-full object-cover"
              />
            </div>
          );
        })}
      </div>

      {selectedPostId && (
        <PostDetailModal postId={selectedPostId} onClose={handleClosePostDetail} />
      )}
    </div>
  );
};
