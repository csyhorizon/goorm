'use client';

import { useState } from 'react';
import { toggleStoreLike } from '@/lib/apis/like.api';

const HeartIcon = ({ isLiked }: { isLiked: boolean }) => (
  <svg
    width="32"
    height="32"
    viewBox="0 0 24 24"
    fill={isLiked ? '#e85757' : 'none'}
    stroke={isLiked ? '#e85757' : 'white'}
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
  </svg>
);

// [수정] Props에 storeId와 initialIsLiked 추가
interface StoreHeaderProps {
  storeId: number;
  name: string;
  category: string;
  initialIsLiked: boolean;
}

export default function StoreHeader({ storeId, name, category, initialIsLiked }: StoreHeaderProps) {
  // [추가] 좋아요 상태 관리 로직
  const [isLiked, setIsLiked] = useState(initialIsLiked);
  const [isLoading, setIsLoading] = useState(false);

  const handleLikeClick = async () => {
    setIsLoading(true);
    try {
      const response = await toggleStoreLike({ storeId });
      // API 응답(becomeLike)에 따라 상태를 업데이트합니다.
      setIsLiked(response.becomeLike);
    } catch (error) {
      console.error("좋아요 처리 실패:", error);
      alert("로그인이 필요하거나 오류가 발생했습니다.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div style={{
      position: 'relative',
      width: '100%',
      height: '250px',
      backgroundColor: '#2c3e50',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'flex-end',
      padding: '24px',
      boxSizing: 'border-box',
      color: 'white',
    }}>
      {/* [추가] 좋아요 버튼 */}
      <button
        onClick={handleLikeClick}
        disabled={isLoading}
        style={{
          position: 'absolute',
          top: '20px',
          right: '20px',
          background: 'rgba(255, 255, 255, 0.2)',
          border: '1px solid rgba(255, 255, 255, 0.3)',
          borderRadius: '50%',
          width: '50px',
          height: '50px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          cursor: 'pointer',
          backdropFilter: 'blur(5px)',
        }}
      >
        <HeartIcon isLiked={isLiked} />
      </button>

      <div>
        <p style={{ margin: '0 0 4px 0', color: '#bdc3c7', fontSize: '1rem' }}>{category}</p>
        <h1 style={{ margin: 0, fontSize: '2.5rem', fontWeight: 'bold' }}>{name}</h1>
      </div>
    </div>
  );
}
