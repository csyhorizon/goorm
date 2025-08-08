import { cookies } from 'next/headers';
import { createServerApi } from '@/lib/apis/serverClient';
import { StoreResponse, ItemResponse, EventResponse } from '@/lib/apis/store.api';
import { PostResponse, Page } from '@/lib/apis/post.api';
import StoreDetail from "@/components/features/store/StoreDetail";

async function getPageData(storeId: number, serverApi: any) {
  const [storeRes, itemsRes, eventsRes, postsRes] = await Promise.all([
    serverApi.get(`/v1/stores/${storeId}`),
    serverApi.get(`/v1/stores/${storeId}/items`),
    serverApi.get(`/v1/stores/${storeId}/events`),
    serverApi.get(`/v1/posts/${storeId}?page=0&size=5&sort=createdAt,desc`), 
  ]);

  return {
    storeData: storeRes.data as StoreResponse,
    itemsData: itemsRes.data as ItemResponse[],
    eventsData: eventsRes.data as EventResponse[],
    postsPage: postsRes.data as Page<PostResponse>,
  };
}

async function checkOwnership(storeId: number, serverApi: any): Promise<boolean> {
  try {
    const myStoreRes = await serverApi.get('/v1/stores/myStore');
    const myStore: StoreResponse = myStoreRes.data;
    return myStore.id === storeId;
  } catch {
    return false;
  }
}

export default async function StoreDetailPage({ params }: { params: Promise<{ id: string }> }) {
  const { id } = await params;
  const storeId = parseInt(id, 10);

  const cookieStore = cookies();
  const serverApi = createServerApi(await cookieStore);

  try {
    const [pageData, isOwner] = await Promise.all([
      getPageData(storeId, serverApi),
      checkOwnership(storeId, serverApi)
    ]);

    return (
      <StoreDetail
        initialStoreData={pageData.storeData}
        initialItems={pageData.itemsData}
        initialEvents={pageData.eventsData} 
        initialPostsPage={pageData.postsPage}
        isOwner={isOwner}
        initialIsLiked={false}
      />
    );

  } catch (error) {
    console.error("가게 상세 정보를 불러오는 데 실패했습니다:", error);
    return (
      <div style={{ padding: '40px', textAlign: 'center' }}>
        <h1>오류</h1>
        <p>가게 정보를 불러오는 데 실패했습니다. 다시 시도해 주세요.</p>
      </div>
    );
  }
}
