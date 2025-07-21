import Link from 'next/link';
import StoreDetail from "@/components/features/store/StoreDetail";

// API mock
async function getStoreData(id: string) {
  const mockStore = {
    id: parseInt(id),
    ownerId: 123, // 가게 주인의 ID (예시)
    name: "다운타우너 안국",
    category: "수제버거",
    imageUrl: 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=1998&auto=format=fit=crop',
    lat: 37.5779,
    lng: 126.9855,
    menuItems: [
      { id: 1, name: "아보카도 버거", price: 9800 },
      { id: 2, name: "베이컨 치즈 버거", price: 8800 },
    ],
    notices: [
      { id: 1, title: "여름 신메뉴 출시!", date: "2025-07-20" },
    ]
  };
  return mockStore;
}

export default async function StoreDetailPage({ params }: { params: Promise<{ id: string }> }) {
  const resolvedParams = await params;
  const store = await getStoreData(resolvedParams.id);
  
  const currentUserId = 123;
  const isOwner = store.ownerId === currentUserId;

  return (
    <div style={{ maxWidth: '1000px', margin: '0 auto', paddingBottom: '80px', position: 'relative' }}>
      {/* 주인일 경우 관리 버튼 표시 */}
      {isOwner && (
        <Link href={`/stores/${store.id}/manage`} style={{
          position: 'absolute',
          top: '20px',
          right: '20px',
          padding: '10px 15px',
          background: 'white',
          color: 'black',
          borderRadius: '8px',
          textDecoration: 'none',
          boxShadow: '0 2px 4px rgba(0,0,0,0.2)',
          zIndex: 10
        }}>
          가게 관리
        </Link>
      )}

      <StoreDetail store={store} />
    </div>
  );
}