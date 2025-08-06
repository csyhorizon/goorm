'use client';

import Link from "next/link";

interface Author {
  nickname: string;
  storeId: number;
}

// const AvatarIcon = ({ url }: { url: string }) => (
//   <Image 
//     src={url} 
//     alt="author avatar" 
//     width={40} 
//     height={40} 
//     style={{ borderRadius: '50%', objectFit: 'cover', marginRight: '12px' }} 
//   />
// );

export default function AuthorInfo({ author }: { author: Author }) {
  return (
    <Link href={`/stores/${author.storeId}`} style={{ textDecoration: 'none', color: 'inherit' }}>
      <div style={{ display: 'flex', alignItems: 'center', cursor: 'pointer', padding: '8px 12px', backgroundColor: '#f1f3f5', borderRadius: '16px' }}>
        <div>
          <p style={{ margin: 0, fontWeight: 500, color: '#495057' }}>{author.nickname}</p>
        </div>
      </div>
    </Link>
  );
}