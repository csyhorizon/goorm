// app/stores/[id]/manage/page.tsx

import StoreManageForm from "@/components/features/store/manage/StoreManageForm";
import { StoreResponse, ItemResponse, EventResponse } from '@/lib/apis/store.api';
import { cookies } from 'next/headers';
import { createServerApi } from '@/lib/apis/serverClient';
import { redirect } from 'next/navigation';

export default async function ManageStorePage({ params }: { params: Promise<{ id: string }> }) {
  const resolvedParams = await params;
  const storeId = parseInt(resolvedParams.id, 10);

  const cookieStore = cookies();
  const serverApi = createServerApi(await cookieStore);

  // --- 1. 권한 확인 단계 ---
  try {
    // '내 가게' 정보를 먼저 조회합니다.
    const myStoreResponse = await serverApi.get('/v1/stores/myStore');
    const myStoreData: StoreResponse = myStoreResponse.data;

    // 내 가게 ID와 접속하려는 가게 ID가 다른지 확인합니다.
    if (myStoreData.id !== storeId) {
      // 다르다면 권한이 없으므로 홈페이지로 리다이렉트합니다.
      console.log("접근 권한 없음: 내 가게 ID와 URL의 가게 ID가 다릅니다.");
      redirect('/forbidden');
    }
  } catch (e) {
    // getMyStore API 호출 자체가 실패하면, 이 사용자는 가게 주인이 아닌 것입니다.
    // 따라서 권한이 없으므로 홈페이지로 리다이렉트합니다.
    console.log("권한 확인 실패: 사용자가 가게를 소유하고 있지 않습니다.");
    redirect('/forbidden');
  }

  // --- 2. 데이터 조회 단계 (권한 확인을 통과한 경우에만 실행됨) ---
  let storeData: StoreResponse | null = null;
  let itemsData: ItemResponse[] = [];
  let eventsData: EventResponse[] = [];
  let error = null;

  try {
    // 이제 안심하고 나머지 데이터를 조회합니다.
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
