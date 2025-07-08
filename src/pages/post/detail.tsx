
import React from 'react';

import { PostDetailView } from '../components/PostDetailView';

const PostDetail = () => {
  
  return (
    <div className="min-h-screen bg-instagram-dark">
      <PostDetailView postId={id} />
    </div>
  );
};

export default PostDetail;
