
import React, { useState } from 'react';
import { Heart, MoreHorizontal } from 'lucide-react';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Api, PostDetailResponse, PostLikeResponse, CommentListResponse, CommentCreateRequest, SuccessResponseCommentResponse, CommentLikeResponse } from '@/api/api'; // Import generated types and Api

// Removed local Comment and Post interfaces as they will be replaced by generated types

interface PostCommentsProps {
  post: PostDetailResponse; // Use the generated PostDetailResponse type
}

export const PostComments: React.FC<PostCommentsProps> = ({ post }) => {
  const [liked, setLiked] = useState(post.likedByMe || false); // Initialize liked state from post data
  const [likesCount, setLikesCount] = useState(post.likeCount || 0); // Initialize likes count
  const [newComment, setNewComment] = useState('');
  const [commentLikes, setCommentLikes] = useState<{[key: number]: boolean}>({}); // This might need to be updated to reflect actual comment likes from API
  const api = new Api(); // Create an instance of the API client

  const handleLike = async () => {
    try {
      const response = await api.posts.likePost(post.postId!); // Call the generated API method
      setLiked(response.data.liked || false);
      setLikesCount(response.data.likeCount || 0);
    } catch (error) {
      console.error('Failed to like post:', error);
      // Handle error (e.g., show a toast notification)
    }
  };

  const handleCommentLike = async (commentId: number) => {
    try {
      const response = await api.comments.likeComment(commentId);
      setCommentLikes(prev => ({
        ...prev,
        [commentId]: response.data.liked || false
      }));
      // Optionally, update the comment's like count in the post object if needed
    } catch (error) {
      console.error('Failed to like comment:', error);
      // Handle error
    }
  };

  const handleSubmitComment = async (e: React.FormEvent) => {
    e.preventDefault();
    if (newComment.trim() && post.postId) {
      try {
        const requestBody: CommentCreateRequest = { content: newComment.trim() };
        const response = await api.posts.createComment(post.postId, requestBody);
        console.log('New comment submitted:', response.data);
        setNewComment('');
        // Optionally, re-fetch comments or add the new comment to the local state
        // For now, let's just log and clear. Re-fetching comments would be ideal.
      } catch (error) {
        console.error('Failed to submit comment:', error);
        // Handle error
      }
    }
  };

  return (
    <div className="flex flex-col h-full">
      {/* Post Header */}
      <div className="flex items-center justify-between p-4 border-b border-instagram-border">
        <div className="flex items-center space-x-3">
          <div className="w-8 h-8 story-ring rounded-full p-0.5">
            <div className="w-full h-full bg-instagram-dark rounded-full p-0.5">
              <img
                src={post.userAvatar || 'https://via.placeholder.com/32'} // Use a placeholder if userAvatar is not available
                alt={post.username}
                className="w-full h-full rounded-full object-cover"
              />
            </div>
          </div>
          <div>
            <div className="flex items-center space-x-1">
              <span className="font-semibold text-sm text-instagram-text">{post.username}</span>
              <span className="text-instagram-muted">•</span>
              <span className="text-sm text-instagram-muted">{post.timeAgo || '방금 전'}</span> {/* timeAgo might not be in PostDetailResponse */}
            </div>
            <span className="text-xs text-instagram-muted">{post.location}</span>
          </div>
        </div>
        <button className="text-instagram-muted hover:text-instagram-text">
          <MoreHorizontal size={20} />
        </button>
      </div>

      {/* Post Caption */}
      <div className="p-4 border-b border-instagram-border">
        <div className="flex items-start space-x-3">
          <img
            src={post.userAvatar || 'https://via.placeholder.com/32'} // Use a placeholder if userAvatar is not available
            alt={post.username}
            className="w-8 h-8 rounded-full object-cover flex-shrink-0"
          />
          <div className="flex-1">
            <div className="text-sm">
              <span className="font-semibold text-instagram-text mr-2">{post.username}</span>
              <span className="text-instagram-text whitespace-pre-wrap">{post.content}</span> {/* Use post.content for caption */}
            </div>
            <div className="mt-2 text-xs text-instagram-muted">{post.timeAgo || '방금 전'}</div> {/* timeAgo might not be in PostDetailResponse */}
          </div>
        </div>
      </div>

      {/* Likes */}
      <div className="px-4 py-3 border-b border-instagram-border">
        <button
          onClick={handleLike}
          className={`flex items-center space-x-1 transition-colors ${
            liked ? 'text-instagram-red' : 'text-instagram-text hover:text-instagram-muted'
          }`}
        >
          <Heart size={20} fill={liked ? 'currentColor' : 'none'} />
        </button>
        <div className="mt-2 text-sm font-semibold text-instagram-text">
          좋아요 {likesCount}개
        </div>
      </div>

      {/* Comments List */}
      <div className="flex-1 overflow-y-auto">
        <div className="space-y-4 p-4">
          {post.comments && post.comments.length > 0 ? (
            post.comments.map((comment) => (
              <div key={comment.commentId} className="flex items-start space-x-3">
                <img
                  src={comment.userAvatar || 'https://via.placeholder.com/32'} // Use a placeholder if userAvatar is not available
                  alt={comment.userName}
                  className="w-8 h-8 rounded-full object-cover flex-shrink-0"
                />
                <div className="flex-1">
                  <div className="text-sm">
                    <span className="font-semibold text-instagram-text mr-2">{comment.userName}</span>
                    <span className="text-instagram-text">{comment.content}</span>
                  </div>
                  <div className="flex items-center space-x-4 mt-1">
                    <span className="text-xs text-instagram-muted">{comment.createdAt ? new Date(comment.createdAt).toLocaleString() : '방금 전'}</span> {/* Use createdAt for timeAgo */}
                    <span className="text-xs text-instagram-muted">좋아요 {comment.likeCount}개</span>
                    <button className="text-xs text-instagram-muted hover:text-instagram-text">답글 달기</button>
                  </div>
                </div>
                <button
                  onClick={() => handleCommentLike(comment.commentId!)}
                  className={`flex-shrink-0 transition-colors ${
                    commentLikes[comment.commentId!] ? 'text-instagram-red' : 'text-instagram-muted hover:text-instagram-text'
                  }`}
                >
                  <Heart size={12} fill={commentLikes[comment.commentId!] ? 'currentColor' : 'none'} />
                </button>
              </div>
            ))
          ) : (
            <div className="text-center text-instagram-muted">댓글이 없습니다.</div>
          )}
        </div>
      </div>

      {/* Comment Input */}
      <div className="border-t border-instagram-border p-4">
        <form onSubmit={handleSubmitComment} className="flex items-center space-x-3">
          <img
            src="https://images.unsplash.com/photo-1649972904349-6e44c42644a7?w=24&h=24&fit=crop&crop=face"
            alt="Your avatar"
            className="w-8 h-8 rounded-full object-cover flex-shrink-0"
          />
          <Input
            type="text"
            placeholder="댓글 달기..."
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            className="flex-1 bg-transparent border-none text-sm text-instagram-text placeholder-instagram-muted focus-visible:ring-0 focus-visible:ring-offset-0"
          />
          <Button
            type="submit"
            disabled={!newComment.trim()}
            className="text-instagram-blue bg-transparent hover:bg-transparent text-sm font-semibold p-0 h-auto disabled:opacity-50"
          >
            게시
          </Button>
        </form>
      </div>
    </div>
  );
};

