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
          // [수정] 두 ID를 모두 사용하여 API를 호출합니다.
          const apiData: PostResponse = await getPostDetail(Number(storeId), Number(postId));

          // API 응답(PostResponse)을 UI 컴포넌트(PostDetail)가 원하는 형태로 변환
          const transformedData: PostDetailData = {
            id: apiData.id,
            title: apiData.title,
            content: apiData.content,
            mediaUrls: apiData.mediaUrls || [], // imageUrl -> mediaUrls로 변경
            author: {
              nickname: apiData.memberName,
              storeId: apiData.storeId,
            },
            createdAt: new Date().toLocaleString(), // API에 없으므로 임시 처리
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