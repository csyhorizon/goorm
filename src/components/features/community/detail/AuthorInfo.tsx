'use client';

import Image from 'next/image';

interface Author {
  nickname: string;
  avatarUrl: string;
}

const AvatarIcon = ({ url }: { url: string }) => (
  <Image 
    src={url} 
    alt="author avatar" 
    width={40} 
    height={40} 
    style={{ borderRadius: '50%', objectFit: 'cover', marginRight: '12px' }} 
  />
);

export default function AuthorInfo({ author }: { author: Author }) {
  return (
    <div style={{ display: 'flex', alignItems: 'center' }}>
      <AvatarIcon url={author.avatarUrl} />
      <div>
        <p style={{ margin: 0, fontWeight: 'bold', color: '#212529' }}>{author.nickname}</p>
      </div>
    </div>
  );
}