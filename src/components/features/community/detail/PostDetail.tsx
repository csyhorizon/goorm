// PostDetail.tsx (최종 수정 코드)
import Image from 'next/image';
import AuthorInfo from './AuthorInfo';
import CommentSection from './CommentSection';

// 타입을 export하여 page.tsx에서도 사용할 수 있게 합니다.
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
          overflowX: 'auto', // 가로 스크롤 활성화
          scrollSnapType: 'x mandatory', // 스크롤 시 이미지에 딱 맞게 멈춤
          WebkitOverflowScrolling: 'touch', // 모바일에서 부드러운 스크롤
          scrollbarWidth: 'none', // 스크롤바 숨기기 (Firefox)
        }}>
          <style>{`
            .scroll-container::-webkit-scrollbar {
              display: none;
            }
          `}</style>

          {post.mediaUrls.map((url, index) => (
            <div key={index} style={{
              flex: '0 0 100%', // 각 아이템이 컨테이너 너비를 100% 차지
              scrollSnapAlign: 'center', // 스크롤 시 중앙에 위치
              position: 'relative',
              aspectRatio: '16 / 10', // 이미지 비율 유지 (레이아웃 깨짐 방지)
            }}>
              <Image 
                src={url}
                alt={`${post.title} 이미지 ${index + 1}`} 
                fill // 부모 요소(div)를 꽉 채우도록 설정
                style={{ objectFit: 'cover' }}
                priority={index === 0}
                sizes="(max-width: 768px) 100vw, 700px" // 뷰포트에 따른 이미지 사이즈 최적화
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
          {/* 수정된 AuthorInfo는 author 객체만 받습니다. */}
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