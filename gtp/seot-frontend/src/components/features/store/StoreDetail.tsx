'use client';

import Link from 'next/link';
import { StoreResponse, ItemResponse, EventResponse } from '@/lib/apis/store.api';
import { PostResponse, Page } from '@/lib/apis/post.api';

import StoreHeader from "./StoreHeader";
import StoreMenuList from "./StoreMenuList";
import StoreNoticeList from "./StoreNoticeList";
import StoreLocationMap from "./StoreLocationMap";
import StorePostList from './StorePostList';

interface StoreDetailProps {
  initialStoreData: StoreResponse;
  initialItems: ItemResponse[];
  initialEvents: EventResponse[];
  initialPostsPage: Page<PostResponse>;
  isOwner: boolean;
  initialIsLiked: boolean;
}

export default function StoreDetail({ 
  initialStoreData, 
  initialItems,
  initialEvents,
  initialPostsPage, 
  isOwner,
  initialIsLiked
}: StoreDetailProps) {
  
  const store = initialStoreData;

  return (
    <div style={{ maxWidth: '1000px', margin: '0 auto', paddingBottom: '80px', position: 'relative' }}>
      {isOwner && (
        <Link href={`/stores/${store.id}/manage`} style={{
          position: 'fixed',
          bottom: '80px',
          right: '20px',
          padding: '10px 15px',
          background: 'white',
          color: 'black',
          borderRadius: '8px',
          textDecoration: 'none',
          boxShadow: '0 2px 4px rgba(0,0,0,0.2)',
          zIndex: 10,
          fontWeight: 'bold',
        }}>
          가게 관리
        </Link>
      )}

      <StoreHeader 
        storeId={store.id}
        name={store.name} 
        category={store.category}
        initialIsLiked={initialIsLiked}
      />
      
      <StoreMenuList menuItems={initialItems} />

      <StoreNoticeList events={initialEvents} />

      <StoreLocationMap lat={store.latitude} lng={store.longitude} />
      
      <StorePostList initialPostsPage={initialPostsPage} storeId={store.id} />
    </div>
  );
}
