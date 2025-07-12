import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Heart, MessageSquare, Plus, MoreHorizontal } from 'lucide-react';
import { HomePostResponse } from '@/api/api';
import { PostDetailModal } from './PostDetailModal'; // ✅ 모달 import

interface FeedPostProps {
  post: HomePostResponse;
  onCommentClick?: () => void;
}

export const FeedPost: React.FC<FeedPostProps> = ({ post, onCommentClick }) => {
  const navigate = useNavigate();
  const [liked, setLiked] = useState(post.likedByMe || false);
  const [saved, setSaved] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const imageUrl =
    post.mediaUrls && post.mediaUrls.length > 0
      ? post.mediaUrls[0]
      : 'https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=400&h=400&fit=crop';

  return (
    <>
      <article className="bg-instagram-dark border border-instagram-border rounded-lg overflow-hidden">
        {/* Post Header */}
        <div className="flex items-center justify-between p-4">
          <div className="flex items-center space-x-3">
            <div className="w-8 h-8 story-ring rounded-full p-0.5">
              <div className="w-full h-full bg-instagram-dark rounded-full p-0.5">
                <img
                  src="https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=32&h=32&fit=crop&crop=face"
                  alt={post.username || 'User'}
                  className="w-full h-full rounded-full object-cover"
                />
              </div>
            </div>
            <div>
              <div className="flex items-center space-x-1">
                <span className="font-semibold text-sm text-instagram-text">
                  {post.username || 'Unknown User'}
                </span>
                <span className="text-instagram-muted">•</span>
                <span className="text-sm text-instagram-muted">방금 전</span>
              </div>
              <span className="text-xs text-instagram-muted">{post.location || ''}</span>
            </div>
          </div>
          <button className="text-instagram-muted hover:text-instagram-text">
            <MoreHorizontal size={20} />
          </button>
        </div>

        {/* Post Image */}
        <div className="relative">
          <img src={imageUrl} alt="Post content" className="w-full h-96 object-cover" />
        </div>

        {/* Post Actions */}
        <div className="p-4 space-y-3">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-4">
              <button
                onClick={() => setLiked(!liked)}
                className={`transition-colors ${
                  liked ? 'text-instagram-red' : 'text-instagram-text hover:text-instagram-muted'
                }`}
              >
                <Heart size={24} fill={liked ? 'currentColor' : 'none'} />
              </button>
              <button
                  onClick={() => {
                    console.log('💬 댓글 버튼 클릭, postId:', post.postId);
                    setShowModal(true);
                  }}
                className="text-instagram-text hover:text-instagram-muted"
              >
                <MessageSquare size={24} />
              </button>
              <button className="text-instagram-text hover:text-instagram-muted">
                <Plus size={24} />
              </button>
            </div>
            <button
              onClick={() => setSaved(!saved)}
              className="text-instagram-text hover:text-instagram-muted"
            >
              <Plus size={24} />
            </button>
          </div>

          {/* Likes Count */}
          <div className="text-sm font-semibold text-instagram-text">
            좋아요 {post.likeCount || 0}개
          </div>

          {/* Caption */}
          <div className="text-sm">
            <span className="font-semibold text-instagram-text mr-2">
              {post.username || 'Unknown User'}
            </span>
            <span className="text-instagram-text">{post.content || ''}</span>
          </div>

          {/* Comments */}
          {post.comments && post.comments.length > 0 && (
            <div className="space-y-1">
              {post.comments.slice(0, 2).map((comment, index) => (
                <div key={index} className="text-sm">
                  <span className="font-semibold text-instagram-text mr-2">
                    {comment.userName || 'Unknown User'}
                  </span>
                  <span className="text-instagram-text">{comment.content || ''}</span>
                </div>
              ))}
              {post.comments.length > 2 && (
                <div className="text-sm text-instagram-muted">
                  댓글 {post.comments.length}개 모두 보기
                </div>
              )}
            </div>
          )}

          {/* Add Comment */}
          <div className="border-t border-instagram-border pt-3">
            <div className="flex items-center space-x-3">
              <div className="w-6 h-6 rounded-full overflow-hidden">
                <img
                  src="https://images.unsplash.com/photo-1649972904349-6e44c42644a7?w=24&h=24&fit=crop&crop=face"
                  alt="Your avatar"
                  className="w-full h-full object-cover"
                />
              </div>
              <input
                type="text"
                placeholder="댓글 달기..."
                className="flex-1 bg-transparent text-sm text-instagram-text placeholder-instagram-muted outline-none"
              />
              <button className="text-instagram-blue text-sm font-semibold">게시</button>
            </div>
          </div>
        </div>
      </article>

      {/* ✅ PostDetailModal 모달 컴포넌트 */}
      {showModal && (
        <PostDetailModal postId={post.postId} onClose={() => setShowModal(false)} />
      )}
    </>
  );
};
