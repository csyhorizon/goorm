import React from 'react';
import { useGetPostQuery, useGetCommentsQuery } from '@/lib/api';
import { PostDetailView } from './PostDetailView';

export const PostDetailModal = ({ postId, onClose }) => {
  const { data: post, isLoading: postLoading, error: postError } = useGetPostQuery(Number(postId));
  const { data: comments, isLoading: commentsLoading } = useGetCommentsQuery(Number(postId));

  if (postLoading || commentsLoading) {
    return (
      <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50">
        <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-white"></div>
      </div>
    );
  }

  if (postError || !post) {
    return (
      <div
        className="fixed inset-0 bg-black/70 flex items-center justify-center z-50"
        onClick={onClose}
      >
        <div
          className="bg-white rounded-lg p-8"
          onClick={(e) => e.stopPropagation()}
        >
          <p className="text-red-500">게시물을 불러오는 중 오류가 발생했습니다.</p>
        </div>
      </div>
    );
  }

  return (
    <div 
      className="fixed inset-0 bg-black/70 flex items-center justify-center z-50"
      onClick={onClose} // 배경 클릭 시 닫기
    >
      <div
        className="bg-white rounded-lg w-full max-w-4xl h-[80vh] flex"
        onClick={(e) => e.stopPropagation()} // 모달 안쪽 클릭 막기
      >
        <PostDetailView post={post} comments={comments || []} onClose={onClose} />
      </div>
    </div>
  );
};
