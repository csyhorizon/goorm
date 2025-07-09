
import React from 'react';
import { useParams } from 'react-router-dom';
import { PostDetailView } from '../../components/PostDetailView';

const PostDetail = () => {
  const { id } = useParams();
  
  return (
    <div className="min-h-screen bg-instagram-dark">
      <PostDetailView postId={id} />
    </div>
  );
};

export default PostDetail;
