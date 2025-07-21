'use client';

import Image from 'next/image';

interface StoreHeaderProps {
  name: string;
  category: string;
  imageUrl: string;
}

export default function StoreHeader({ name, category, imageUrl }: StoreHeaderProps) {
  return (
    <div style={{ position: 'relative', width: '100%', height: '300px' }}>
      <Image 
        src={imageUrl} 
        alt={name} 
        fill
        style={{ objectFit: 'cover' }} 
      />
      <div style={{ position: 'absolute', bottom: 0, left: 0, right: 0, padding: '16px', background: 'linear-gradient(to top, rgba(0,0,0,0.7), transparent)' }}>
        <h1 style={{ margin: 0, color: 'white', fontSize: '2rem' }}>{name}</h1>
        <p style={{ margin: '4px 0 0', color: '#eee', fontSize: '1rem' }}>{category}</p>
      </div>
    </div>
  );
}