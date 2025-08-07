'use client';

import Link from 'next/link';
import { StoreResponse, ItemResponse, EventResponse } from '@/lib/apis/store.api';
import { PostResponse, Page } from '@/lib/apis/post.api';

// 아래에서 만들 하위 컴포넌트들을 import 합니다.
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
}

export default function StoreDetail({ 
  initialStoreData, 
  initialItems,
  initialEvents,
  initialPostsPage, 
  isOwner 
}: StoreDetailProps) {
  
  const store = initialStoreData;

  return (
    <div style={{ maxWidth: '1000px', margin: '0 auto', paddingBottom: '80px', position: 'relative' }}>
      {/* 주인일 경우에만 '가게 관리' 버튼 표시 */}
      {isOwner && (
        <Link href={`/stores/${store.id}/manage`} style={{
          position: 'fixed', // 화면에 고정
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

      {/* 가게 대표 이미지 및 이름 */}
      <StoreHeader 
        name={store.name} 
        category={store.category}
      />
      
      {/* 메뉴 목록 */}
      <StoreMenuList menuItems={initialItems} />

      {/* 공지/이벤트 (게시글 기반) */}
      <StoreNoticeList events={initialEvents} />

      {/* 가게 위치 지도 */}
      <StoreLocationMap lat={store.latitude} lng={store.longitude} />
      
      {/* 가게 전체 게시글 목록 */}
      <StorePostList initialPostsPage={initialPostsPage} storeId={store.id} />
    </div>
  );
}
