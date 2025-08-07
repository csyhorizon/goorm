'use client';

import { useState, useEffect, useCallback } from 'react';
import { useInView } from 'react-intersection-observer';
import { getPostsByStore, PostResponse, Page } from '@/lib/apis/post.api';
import Link from 'next/link';
import Image from 'next/image';

const PostItem = ({ post }: { post: PostResponse }) => {
    const thumbnailUrl = post.mediaUrls && post.mediaUrls.length > 0
        ? post.mediaUrls[0]
        : '/default-thumbnail.png';

    return (
        <Link href={`/community/${post.storeId}/${post.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
            <article style={{ display: 'flex', padding: '16px', borderBottom: '1px solid #eaeaea' }}>
                <div style={{ width: '100px', height: '100px', marginRight: '16px', flexShrink: 0, position: 'relative' }}>
                    <Image src={thumbnailUrl} alt={post.title} fill style={{ objectFit: 'cover', borderRadius: '8px' }} />
                </div>
                <div style={{ flex: 1 }}>
                    <h3 style={{ margin: 0 }}>{post.title}</h3>
                    <p style={{ margin: '4px 0', color: '#555', fontSize: '0.9rem' }}>{post.content.substring(0, 50)}...</p>
                </div>
            </article>
        </Link>
    );
}

const Loader = () => <div style={{ textAlign: 'center', padding: '20px' }}>불러오는 중...</div>;

export default function StorePostList({ initialPostsPage, storeId }: { initialPostsPage: Page<PostResponse>; storeId: number; }) {
  const [posts, setPosts] = useState<PostResponse[]>(initialPostsPage.content);
  const [page, setPage] = useState<number>(1); // 0번 페이지는 이미 로드됨
  const [hasMore, setHasMore] = useState<boolean>(!initialPostsPage.last);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const { ref, inView } = useInView({ threshold: 0 });

  const loadMorePosts = useCallback(async () => {
    if (isLoading || !hasMore) return;
    setIsLoading(true);
    try {
      const data = await getPostsByStore(storeId, { page, size: 5, sort: ["createdAt,desc"] });
      setPosts(prev => [...prev, ...data.content]);
      setPage(prev => prev + 1);
      setHasMore(!data.last);
    } catch (error) {
      console.error("게시글을 추가로 불러오는 데 실패했습니다:", error);
    } finally {
      setIsLoading(false);
    }
  }, [storeId, page, isLoading, hasMore]);

  useEffect(() => {
    if (inView && hasMore && !isLoading) {
      loadMorePosts();
    }
  }, [inView, hasMore, isLoading, loadMorePosts]);

  return (
    <section style={{ padding: '16px' }}>
      <h2 style={{ fontSize: '1.2rem', marginBottom: '16px' }}>가게 소식</h2>
      <div>
        {posts.map(post => <PostItem key={post.id} post={post} />)}
      </div>
      <div ref={ref}>
        {isLoading && <Loader />}
        {!hasMore && posts.length > 0 && <div style={{ textAlign: 'center', padding: '20px', color: '#888' }}>모든 소식을 불러왔습니다.</div>}
      </div>
    </section>
  );
}
