// app/stores/[id]/manage/page.tsx

import StoreManageForm from "@/components/features/store/StoreManageForm";
import { getStoreDetail, StoreResponse, getStoreItems, ItemResponse, getStoreEvents, EventResponse } from '@/lib/apis/store.api';
import { cookies } from 'next/headers';
import { createServerApi } from '@/lib/apis/serverClient';

// Next.js 서버 컴포넌트로 데이터를 불러옵니다.
export default async function ManageStorePage({ params }: { params: { id: string } }) {
  const storeId = parseInt(params.id, 10);
  let storeData: StoreResponse | null = null;
  let itemsData: ItemResponse[] = [];
  let eventsData: EventResponse[] = [];
  let error = null;

  // 1. `cookies()`를 사용하여 쿠키 저장소를 가져옵니다.
  const cookieStore = cookies();
  // 2. `createServerApi`를 사용해 쿠키가 포함된 Axios 인스턴스를 생성합니다.
  const serverApi = createServerApi(await cookieStore);

  try {
    // 3. getStoreDetail, getStoreItems, getStoreEvents 함수를 수정하여
    //    serverApi 인스턴스를 인자로 받도록 변경하거나,
    //    아래처럼 직접 API 호출을 하도록 합니다.
    const [storeResponse, itemsResponse, eventsResponse] = await Promise.all([
      serverApi.get(`/v1/stores/${storeId}`),
      serverApi.get(`/v1/stores/${storeId}/items`),
      serverApi.get(`/v1/stores/${storeId}/events`),
    ]);
    storeData = storeResponse.data;
    itemsData = itemsResponse.data;
    eventsData = eventsResponse.data;

  } catch (e) {
    console.error("가게 관리 데이터를 불러오는 데 실패했습니다:", e);
    error = "가게 정보를 불러오는 데 실패했습니다. ID를 확인하거나 다시 시도해 주세요.";
  }

  if (error || !storeData) {
    return (
      <div style={{ maxWidth: '1000px', margin: '40px auto', padding: '20px' }}>
        <h1 style={{ marginBottom: '20px' }}>오류 발생</h1>
        <p style={{ color: 'red' }}>{error || "가게 정보를 찾을 수 없습니다."}</p>
      </div>
    );
  }

  return (
    <div style={{ maxWidth: '1000px', margin: '40px auto', padding: '20px' }}>
      <h1 style={{ marginBottom: '20px' }}>{storeData.name} 가게 관리</h1>
      <StoreManageForm 
        storeId={storeId} 
        initialStoreData={storeData} 
        initialItems={itemsData}
        initialEvents={eventsData}
      />
    </div>
  );
}