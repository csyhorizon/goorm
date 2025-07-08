import React, { useState } from 'react';
import { Heart, MessageSquare, Plus, MoreHorizontal } from 'lucide-react';


interface Post {
  id: number;
  username: string;
  location: string;
  timeAgo: string;
  image: string;
  likes: number;
  caption: string;
  comments: Array<{ username: string; comment: string; }>;
}

interface FeedPostProps {
  post: Post;
}

export const FeedPost: React.FC<FeedPostProps> = ({ post }) => {
  const [liked, setLiked] = useState(false);
  const [saved, setSaved] = useState(false);
  const handleCommentClick = () => {
    window.location.href = `/post/${post.id}`;
  };

  return (
    <article className="bg-instagram-dark border border-instagram-border rounded-lg overflow-hidden">
      {/* Post Header */}
      <div className="flex items-center justify-between p-4">
        <div className="flex items-center space-x-3">
          <div className="w-8 h-8 story-ring rounded-full p-0.5">
            <div className="w-full h-full bg-instagram-dark rounded-full p-0.5">
              <img
                src="https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=32&h=32&fit=crop&crop=face"
                alt={post.username}
                className="w-full h-full rounded-full object-cover"
              />
            </div>
          </div>
          <div>
            <div className="flex items-center space-x-1">
              <span className="font-semibold text-sm text-instagram-text">{post.username}</span>
              <span className="text-instagram-muted">•</span>
              <span className="text-sm text-instagram-muted">{post.timeAgo}</span>
            </div>
            <span className="text-xs text-instagram-muted">{post.location}</span>
          </div>
        </div>
        <button className="text-instagram-muted hover:text-instagram-text">
          <MoreHorizontal size={20} />
        </button>
      </div>

      {/* Post Image */}
      <div className="relative">
        <img
          src={post.image}
          alt="Post content"
          className="w-full h-96 object-cover"
        />
      </div>

      {/* Post Actions */}
      <div className="p-4 space-y-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-4">
            <button 
              onClick={() => setLiked(!liked)}
              className={`transition-colors ${liked ? 'text-instagram-red' : 'text-instagram-text hover:text-instagram-muted'}`}
            >
              <Heart size={24} fill={liked ? 'currentColor' : 'none'} />
            </button>
            <button 
              onClick={handleCommentClick}
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
          좋아요 {liked ? post.likes + 1 : post.likes}개
        </div>

        {/* Caption */}
        <div className="text-sm">
          <span className="font-semibold text-instagram-text mr-2">{post.username}</span>
          <span className="text-instagram-text">{post.caption}</span>
        </div>

        {/* Comments */}
        {post.comments.length > 0 && (
          <div className="space-y-1">
            {post.comments.map((comment, index) => (
              <div key={index} className="text-sm">
                <span className="font-semibold text-instagram-text mr-2">{comment.username}</span>
                <span className="text-instagram-text">{comment.comment}</span>
              </div>
            ))}
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
  );
};
