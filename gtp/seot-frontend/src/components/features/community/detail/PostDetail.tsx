import Image from 'next/image';
import AuthorInfo from './AuthorInfo';
import CommentSection from './CommentSection';

export interface PostDetailData {
  id: number;
  title: string;
  author: {
    nickname: string;
    storeId: number;
  };
  createdAt?: string;
  content: string;
  mediaUrls: string[];
  price?: number;
  status?: string;
}

export default function PostDetail({ post }: { post: PostDetailData }) {
  return (
    <div style={{ maxWidth: '700px', margin: '0 auto', paddingBottom: '80px' }}>
      
      {post.mediaUrls && post.mediaUrls.length > 0 && (
        <div style={{ 
          display: 'flex', 
          overflowX: 'auto',
          scrollSnapType: 'x mandatory',
          WebkitOverflowScrolling: 'touch',
          scrollbarWidth: 'none',
        }}>
          <style>{`
            .scroll-container::-webkit-scrollbar {
              display: none;
            }
          `}</style>

          {post.mediaUrls.map((url, index) => (
            <div key={index} style={{
              flex: '0 0 100%',
              scrollSnapAlign: 'center',
              position: 'relative',
              aspectRatio: '16 / 10',
            }}>
              <Image 
                src={url}
                alt={`${post.title} 이미지 ${index + 1}`} 
                fill
                style={{ objectFit: 'cover' }}
                priority={index === 0}
                sizes="(max-width: 768px) 100vw, 700px"
              />
            </div>
          ))}
        </div>
      )}
      
      <div style={{ padding: '16px' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', paddingBottom: '16px' }}>
          <div>
            <h1 style={{ margin: 0, fontSize: '1.5rem' }}>{post.title}</h1>
            {post.createdAt && (
              <p style={{ margin: '8px 0 0', color: '#868e96', fontSize: '0.9rem' }}>
                {post.createdAt}
              </p>
            )}
          </div>
          <AuthorInfo author={post.author} />
        </div>

        {(post.price || post.status) && (
          <div style={{ borderTop: '1px solid #eaeaea', borderBottom: '1px solid #eaeaea', padding: '16px 0', margin: '16px 0' }}>
            {post.price ? (
              <p style={{ margin: 0, fontSize: '1.2rem', fontWeight: 'bold' }}>{post.price.toLocaleString()}원</p>
            ) : (
              <p style={{ margin: 0, fontSize: '1rem', fontWeight: 'bold', color: '#555' }}>{post.status}</p>
            )}
          </div>
        )}

        <p style={{ fontSize: '1rem', lineHeight: '1.6', minHeight: '150px', margin: '20px 0', whiteSpace: 'pre-wrap' }}>
          {post.content}
        </p>
      </div>

      <CommentSection postId={post.id} />
    </div>
  );
}