
import React from 'react';
import { StoryCarousel } from '@/components/StoryCarousel';
import { FeedPost } from '@/components/FeedPost';
import { Post } from '@/lib/api';

interface MainFeedProps {
  posts: Post[];
}

const MainFeed: React.FC<MainFeedProps> = ({ posts }) => {
  return (
    <div className="flex-1 max-w-2xl mx-auto">
      {/* Story Carousel */}
      <div className="pt-6 pb-4">
        <StoryCarousel />
      </div>

      {/* Feed Posts */}
      <div className="space-y-6">
        {posts.length > 0 ? (
          posts.map((post) => (
            <FeedPost key={post.id} post={post} />
          ))
        ) : (
          <div className="text-center py-8 text-instagram-muted">게시물이 없습니다.</div>
        )}
      </div>
    </div>
  );
};

export default MainFeed;

