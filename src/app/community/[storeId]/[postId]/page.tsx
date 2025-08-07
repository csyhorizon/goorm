'use client';

import { useState, useEffect } from 'react';
import { useParams } from 'next/navigation';
import { getPostDetail, PostResponse } from '@/lib/apis/post.api';
import PostDetail, { PostDetailData } from '@/components/features/community/detail/PostDetail';

export default function PostDetailPage() {
  const [post, setPost] = useState<PostDetailData | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  
  const params = useParams();
  const storeId = params.storeId as string;
  const postId = params.postId as string;

  useEffect(() => {
    if (storeId && postId) {
      const fetchPostData = async () => {
        setIsLoading(true);
        try {
          const apiData: PostResponse = await getPostDetail(Number(storeId), Number(postId));

          const transformedData: PostDetailData = {
            id: apiData.id,
            title: apiData.title,
            content: apiData.content,
            mediaUrls: apiData.mediaUrls || [],
            author: {
              nickname: apiData.memberName,
              storeId: apiData.storeId,
            },
            createdAt: new Date().toLocaleString(),
          };
          setPost(transformedData);
        } catch (e) {
          console.error(e);
        } finally {
          setIsLoading(false);
        }
      };
      fetchPostData();
    }
  }, [storeId, postId]);

  if (isLoading) return <div>로딩 중...</div>;
  if (!post) return <div>게시글을 찾을 수 없습니다.</div>;

  return (
    <div style={{ maxWidth: '1000px', margin: '0 auto' }}>
      <PostDetail post={post} />
    </div>
  );
}