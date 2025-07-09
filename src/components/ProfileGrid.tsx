
import React from 'react';

interface ProfileGridProps {
  userId: number;
  type?: 'posts' | 'reels' | 'tagged';
}

export const ProfileGrid: React.FC<ProfileGridProps> = ({ type = 'posts' }) => {
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

  return (
    <div className="flex flex-col items-center justify-center py-16 text-instagram-muted">
      <div className="text-4xl mb-4">📷</div>
      <h3 className="text-lg font-medium mb-2">아직 게시물이 없습니다</h3>
      <p className="text-sm">첫 번째 게시물을 공유해보세요!</p>
    </div>
  );
};
