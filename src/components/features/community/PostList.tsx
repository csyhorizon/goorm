'use client';

import { useState, useEffect, useCallback } from 'react';
import { useInView } from 'react-intersection-observer';
import axios, { AxiosError } from 'axios';
import { getPostsByStore, PostResponse, Page } from '@/lib/apis/post.api';
import PostItem from './PostItem';
import FloatingWriteButton from './FloatingWriteButton';

const Loader = () => <div style={{ textAlign: 'center', padding: '20px' }}>불러오는 중...</div>;

export default function PostList({ storeId }: { storeId: number }) {
  const [posts, setPosts] = useState<PostResponse[]>([]);
  const [page, setPage] = useState<number>(0);
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const { ref, inView } = useInView({
    threshold: 0,
  });

  const loadMorePosts = useCallback(async (isInitialLoad = false) => {
    if (!isInitialLoad && (isLoading || !hasMore)) return;

    setIsLoading(true);
    try {
      const data: Page<PostResponse> = await getPostsByStore(storeId, { page, size: 10 });

      setPosts(prevPosts => [...prevPosts, ...data.content]);
      setPage(prevPage => prevPage + 1);
      setHasMore(!data.last);

    } catch (error) {
      console.error("게시글을 불러오는 데 실패했습니다:", error);

      if (axios.isAxiosError(error)) {
        const axiosError = error as AxiosError;

        if (axiosError.response && (axiosError.response.status === 401 || axiosError.response.status === 419)) {
          console.log("인증 실패, 로그인 페이지로 이동합니다.");
          setHasMore(false);
          window.location.href = '/auth/login';
        }
      } else {
        console.error("알 수 없는 오류가 발생했습니다:", error);
      }
    } finally {
      setIsLoading(false);
    }
  }, [storeId, page, isLoading, hasMore]);

  // 'storeId'가 변경될 때마다 새로운 게시글을 초기화하고 불러옵니다.
  useEffect(() => {
    setPosts([]);
    setPage(0);
    setHasMore(true);
    setIsLoading(false);
    loadMorePosts(true); // 초기 로딩임을 명시
  }, [storeId]);

  // 'inView' 상태가 변경될 때마다 추가 게시글을 불러옵니다.
  useEffect(() => {
    if (inView && hasMore && !isLoading) {
      loadMorePosts();
    }
  }, [inView, hasMore, isLoading]);

  return (
    <div>
      {posts.map(post => (
        <PostItem key={post.id} post={post} />
      ))}

      <div ref={ref} style={{ height: '50px' }}>
        {isLoading && <Loader />}
        {!hasMore && posts.length > 0 &&
          <div style={{ textAlign: 'center', padding: '20px', color: '#868e96' }}>
            모든 게시글을 불러왔습니다.
          </div>
        }
      </div>

      <FloatingWriteButton />
    </div>
  );
}