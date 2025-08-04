'use client';

import { useState, useEffect } from 'react';
import { useInView } from 'react-intersection-observer';
import { getPostsByStore, PostResponse, Page } from '@/lib/apis/post.api';
import PostItem from './PostItem';
import FloatingWriteButton from './FloatingWriteButton';

const Loader = () => <div style={{ textAlign: 'center', padding: '20px' }}>불러오는 중...</div>;

export default function PostList({ storeId }: { storeId: number }) {
  const [posts, setPosts] = useState<PostResponse[]>([]);
  const [page, setPage] = useState<number>(0);
  const [hasMore, setHasMore] = useState<boolean>(true); // 더 불러올 데이터가 있는지 여부
  const [isLoading, setIsLoading] = useState<boolean>(false); // 데이터 로딩 중인지 여부

  // useInView 훅: ref가 화면에 보이면 inView가 true가 됩니다.
  const { ref, inView } = useInView({
    threshold: 0, // 요소가 1px만 보여도 트리거
  });

  // 게시글을 불러오는 함수
  const loadMorePosts = async () => {
    // 로딩 중이거나 더 이상 불러올 게시글이 없으면 중단
    if (isLoading || !hasMore) return;

    setIsLoading(true);
    try {
      // API 호출로 다음 페이지 데이터 가져오기
      const data: Page<PostResponse> = await getPostsByStore(storeId, { page, size: 10 });

      // 새로 불러온 데이터를 기존 posts 배열에 추가
      setPosts(prevPosts => [...prevPosts, ...data.content]);
      // 다음 페이지 번호를 설정
      setPage(prevPage => prevPage + 1);
      // 마지막 페이지인지 여부를 hasMore 상태에 저장
      setHasMore(!data.last);

    } catch (error) {
      console.error("게시글을 불러오는 데 실패했습니다:", error);
    } finally {
      setIsLoading(false);
    }
  };

  // 컴포넌트가 처음 마운트될 때 첫 페이지 데이터를 불러옵니다.
  useEffect(() => {
    loadMorePosts();
  }, [storeId]); // storeId가 바뀌면 새로 데이터를 불러옵니다.

  // 'ref'가 달린 요소가 화면에 보이고, 더 불러올 데이터가 있을 때 loadMorePosts 함수를 호출합니다.
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

      {/* 이 div가 화면에 보이면 다음 페이지를 로드합니다. */}
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