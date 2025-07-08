
import React, { useEffect, useState } from 'react';
import { ArrowLeft } from 'lucide-react';
import { PostImageCarousel } from './PostImageCarousel';
import { PostComments } from './PostComments';
import { Api, PostDetailResponse } from '@/api/api'; // Api 클래스와 PostDetailResponse 타입 임포트

interface PostDetailViewProps {
  postId?: string;
}

export const PostDetailView: React.FC<PostDetailViewProps> = ({ postId }) => {
  const [post, setPost] = useState<PostDetailResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const api = new Api(); // Api 클래스의 인스턴스 생성

  useEffect(() => {
    const fetchPostDetail = async () => {
      if (!postId) {
        setError('게시물 ID가 제공되지 않았습니다.');
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        // getPostDetail API 호출
        const response = await api.posts.getPostDetail(Number(postId));
        setPost(response.data); // 응답 데이터로 게시물 상태 업데이트
      } catch (err) {
        console.error('Failed to fetch post detail:', err);
        setError('게시물 상세 정보를 불러오는 데 실패했습니다.');
      } finally {
        setLoading(false);
      }
    };

    fetchPostDetail();
  }, [postId]);

  const handleBack = () => {
    window.history.back();
  };

  if (loading) {
    return <div className="min-h-screen bg-instagram-dark text-center py-8">게시물 상세 정보를 불러오는 중...</div>;
  }

  if (error) {
    return <div className="min-h-screen bg-instagram-dark text-center py-8 text-red-500">{error}</div>;
  }

  if (!post) {
    return <div className="min-h-screen bg-instagram-dark text-center py-8">게시물을 찾을 수 없습니다.</div>;
  }

  return (
    <div className="min-h-screen bg-instagram-dark">
      {/* Header */}
      <div className="sticky top-0 z-10 bg-instagram-dark border-b border-instagram-border px-4 py-3">
        <div className="flex items-center space-x-4">
          <button
            onClick={handleBack}
            className="text-instagram-text hover:text-instagram-muted transition-colors"
          >
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-lg font-semibold text-instagram-text">게시물</h1>
        </div>
      </div>

      {/* Main Content */}
      <div className="flex flex-col lg:flex-row min-h-[calc(100vh-65px)]">
        {/* Left: Image Carousel */}
        <div className="lg:flex-[3] bg-black flex items-center justify-center">
          <PostImageCarousel images={post.mediaUrls || []} />
        </div>

        {/* Right: Post Details & Comments */}
        <div className="lg:flex-[2] bg-instagram-dark border-l border-instagram-border flex flex-col">
          <PostComments post={post} />
        </div>
      </div>
    </div>
  );
};
