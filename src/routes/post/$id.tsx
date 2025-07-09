import React from 'react';
import { useParams } from 'react-router-dom';
import { useGetPostQuery, useGetCommentsQuery } from '@/lib/api';

export default function PostDetail() {
  const { id } = useParams();
  const postId = parseInt(id || '1');
  
  const { data: post, isLoading: postLoading } = useGetPostQuery(postId);
  const { data: comments, isLoading: commentsLoading } = useGetCommentsQuery(postId);

  if (postLoading || commentsLoading) {
    return (
      <div className="min-h-screen bg-instagram-dark flex items-center justify-center">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-white"></div>
      </div>
    );
  }

  if (!post) {
    return (
      <div className="min-h-screen bg-instagram-dark flex items-center justify-center">
        <div className="text-white text-xl">게시물을 찾을 수 없습니다.</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-instagram-dark">
      {/* 임시로 간단한 포스트 상세 표시 */}
      <div className="p-4">
        <h1 className="text-white text-2xl mb-4">포스트 #{postId}</h1>
        <p className="text-gray-300">{post.content}</p>
        <div className="mt-4 text-gray-400">
          댓글 수: {comments?.length || 0}
        </div>
      </div>
    </div>
  );
} 