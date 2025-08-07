'use client';

import { useState, useEffect } from 'react';
import { getAllPosts, PostResponse, Page } from '@/lib/apis/post.api';
import PostItem from './PostItem';
import FloatingWriteButton from './FloatingWriteButton';

export default function PostList() {
  const [posts, setPosts] = useState<PostResponse[]>([]);
  const [page, setPage] = useState<number>(0); // 현재 페이지 번호 (0부터 시작)
  const [totalPages, setTotalPages] = useState<number>(1);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const data: Page<PostResponse> = await getAllPosts({ page, size: 10 });
        setPosts(data.content);
        setTotalPages(data.totalPages);
      } catch (error) {
        console.error("게시글 불러오기 실패", error);
      }
    };

    fetchPosts();
  }, [page]);

  const handlePageClick = (pageNumber: number) => {
    setPage(pageNumber);
  };

  return (
    <div>
      {posts.map(post => (
        <PostItem key={post.id} post={post} />
      ))}

      {/* 페이지네이션 */}
      <div style={{ textAlign: 'center', marginTop: '20px' }}>
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i}
            onClick={() => handlePageClick(i)}
            style={{
              margin: '0 5px',
              padding: '5px 10px',
              backgroundColor: page === i ? '#333' : '#eee',
              color: page === i ? '#fff' : '#000',
              borderRadius: '5px',
              border: 'none',
              cursor: 'pointer',
            }}
          >
            {i + 1}
          </button>
        ))}
      </div>

      <FloatingWriteButton />
    </div>
  );
}
