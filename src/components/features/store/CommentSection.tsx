'use client';

import { useState } from 'react';
import Image from 'next/image';

interface Comment {
  id: number;
  author: {
    nickname: string;
    avatarUrl: string;
  };
  content: string;
  createdAt: string;
}

const CommentItem = ({ comment }: { comment: Comment }) => (
  <div style={{ display: 'flex', padding: '12px 0', borderBottom: '1px solid #f2f3f5' }}>
    <div style={{ width: '40px', height: '40px', borderRadius: '50%', overflow: 'hidden', marginRight: '12px', flexShrink: 0 }}>
      <Image 
        src={comment.author.avatarUrl} 
        alt="avatar" 
        width={40} 
        height={40} 
        style={{ width: '100%', height: '100%', objectFit: 'cover' }} 
      />
    </div>
    <div style={{ flex: 1 }}>
      <p style={{ margin: 0, fontWeight: 'bold', fontSize: '0.9rem' }}>{comment.author.nickname}</p>
      <p style={{ margin: '4px 0 0', fontSize: '0.9rem' }}>{comment.content}</p>
      <p style={{ margin: '8px 0 0', fontSize: '0.75rem', color: '#868e96' }}>{comment.createdAt}</p>
    </div>
  </div>
);

export default function CommentSection() {
  const [comments, setComments] = useState<Comment[]>([
    { id: 1, author: { nickname: '네고왕', avatarUrl: 'https://i.pravatar.cc/150?img=1' }, content: '가격 네고 가능한가요?', createdAt: '3분 전' },
    { id: 2, author: { nickname: '댕댕이주인', avatarUrl: 'https://i.pravatar.cc/150?img=2' }, content: '아니요, 네고는 어렵습니다 ㅠㅠ', createdAt: '1분 전' },
  ]);
  const [newComment, setNewComment] = useState('');

  const handleCommentSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newComment.trim()) return;
    
    const newCommentData: Comment = {
        id: Date.now(),
        author: { nickname: "나", avatarUrl: 'https://i.pravatar.cc/150?img=3' },
        content: newComment,
        createdAt: '방금 전'
    };
    setComments(prev => [...prev, newCommentData]);
    setNewComment('');
  };

  return (
    <div style={{ padding: '16px' }}>
      <h4 style={{ margin: '0 0 16px' }}>댓글 {comments.length}</h4>
      {comments.map(comment => (
        <CommentItem key={comment.id} comment={comment} />
      ))}

      <form onSubmit={handleCommentSubmit} style={{
        position: 'fixed',
        bottom: '60px',
        left: 0,
        right: 0,
        maxWidth: '1000px',
        margin: '0 auto',
        padding: '8px 16px',
        borderTop: '1px solid #eaeaea',
        backgroundColor: 'white',
        display: 'flex',
        alignItems: 'center',
      }}>
        <input
          type="text"
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          placeholder="댓글을 입력하세요..."
          style={{
            flex: 1,
            padding: '10px',
            borderRadius: '20px',
            border: '1px solid #ddd',
            marginRight: '8px',
          }}
        />
        <button type="submit" style={{
          padding: '10px 16px',
          borderRadius: '20px',
          border: 'none',
          backgroundColor: '#ff6f0f',
          color: 'white',
          cursor: 'pointer',
        }}>
          등록
        </button>
      </form>
    </div>
  );
}