
import React from 'react';
import { ArrowLeft } from 'lucide-react';
import { PostImageCarousel } from './PostImageCarousel';
import { PostComments } from './PostComments';
import { Post, Comment } from '@/lib/api';

interface PostDetailViewProps {
  post: Post;
  comments: Comment[];
}

export const PostDetailView: React.FC<PostDetailViewProps> = ({ post, comments }) => {
  const handleBack = () => {
    window.history.back();
  };

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
          <PostImageCarousel images={post.images || []} />
        </div>

        {/* Right: Post Details & Comments */}
        <div className="lg:flex-[2] bg-instagram-dark border-l border-instagram-border flex flex-col">
          <PostComments post={post} comments={comments} />
        </div>
      </div>
    </div>
  );
};
