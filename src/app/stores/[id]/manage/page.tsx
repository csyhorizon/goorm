import StoreManageForm from "@/components/features/store/StoreManageForm";

// API mock
async function getStoreData(id: string) {
  const mockNotices = [
      { id: 1, title: "여름 신메뉴 출시!", content: "..." },
      { id: 2, title: "매주 월요일은 정기 휴무입니다.", content: "..." },
  ];
  return { id: parseInt(id), notices: mockNotices };
}

export default async function ManageStorePage({ params }: { params: Promise<{ id: string }> }) {
  const resolvedParams = await params;
  const storeData = await getStoreData(resolvedParams.id);

  return (
    <div style={{ maxWidth: '1000px', margin: '40px auto', padding: '20px' }}>
      <h1 style={{ marginBottom: '20px' }}>가게 관리</h1>
      <StoreManageForm initialNotices={storeData.notices} storeId={resolvedParams.id} />
    </div>
  );
}