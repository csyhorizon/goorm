import Image from 'next/image';
import AuthorInfo from './AuthorInfo';
import CommentSection from './CommentSection';

interface PostDetailData {
  id: number;
  title: string;
  author: {
    nickname: string;
    avatarUrl: string;
  };
  createdAt: string;
  content: string;
  imageUrl: string;
  price?: number;
  status?: string;
}

export default function PostDetail({ post }: { post: PostDetailData }) {
  return (
    <div style={{ paddingBottom: '80px' }}>
      <Image 
        src={post.imageUrl} 
        alt={post.title} 
        width={400} 
        height={300} 
        style={{ width: '100%', height: '300px', objectFit: 'cover' }} 
      />
      <div style={{ padding: '16px' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', paddingBottom: '16px' }}>
          <div>
            <h1 style={{ margin: 0, fontSize: '1.5rem' }}>{post.title}</h1>
            <p style={{ margin: '8px 0 0', color: '#868e96', fontSize: '0.9rem' }}>
              {post.createdAt}
            </p>
          </div>
          <AuthorInfo author={post.author} />
        </div>
        
        {/* 가격 정보 */}
        <div style={{ borderTop: '1px solid #eaeaea', borderBottom: '1px solid #eaeaea', padding: '16px 0', margin: '16px 0' }}>
          {post.price ? (
            <p style={{ margin: 0, fontSize: '1.2rem', fontWeight: 'bold' }}>{post.price.toLocaleString()}원</p>
          ) : (
            <p style={{ margin: 0, fontSize: '1rem', fontWeight: 'bold', color: '#555' }}>{post.status || '가격 정보 없음'}</p>
          )}
        </div>

        {/* 게시글 내용 */}
        <p style={{ fontSize: '1rem', lineHeight: '1.6', minHeight: '150px', margin: '20px 0' }}>
          {post.content}
        </p>
      </div>

      <CommentSection />
    </div>
  );
}