'use client';

import { useState } from 'react';
import { 
  StoreResponse, 
  ItemResponse, 
  EventResponse, 
  CreateItemRequest, 
  createStoreItem, 
  deleteStoreItem, 
} from '@/lib/apis/store.api';

// 탭 컴포넌트를 위한 props 정의
interface TabProps {
  label: string;
  onClick: () => void;
  isActive: boolean;
}

// 탭 UI 컴포넌트
const Tab = ({ label, onClick, isActive }: TabProps) => (
  <button
    onClick={onClick}
    style={{
      padding: '10px 20px',
      borderBottom: isActive ? '2px solid #4a90e2' : '2px solid transparent',
      color: isActive ? '#4a90e2' : '#555',
      fontWeight: isActive ? 'bold' : 'normal',
      cursor: 'pointer',
      backgroundColor: 'transparent',
      // 'border: 'none'' 속성을 제거하여 'borderBottom'과의 충돌을 방지합니다.
      // 다른 테두리가 필요 없다면 명시적으로 'borderTop: "none"' 등을 추가할 수 있습니다.
      borderTop: 'none',
      borderLeft: 'none',
      borderRight: 'none',
      fontSize: '1rem'
    }}
  >
    {label}
  </button>
);

interface StoreManageFormProps {
  storeId: number;
  initialStoreData: StoreResponse;
  initialItems: ItemResponse[];
  initialEvents: EventResponse[];
}

// 메인 관리 폼 컴포넌트
export default function StoreManageForm({ storeId, initialStoreData, initialItems, initialEvents }: StoreManageFormProps) {
  const [activeTab, setActiveTab] = useState('info');
  const [items, setItems] = useState<ItemResponse[]>(initialItems);
  const [events, setEvents] = useState<EventResponse[]>(initialEvents);
  const [error, setError] = useState<string | null>(null);

  const handleCreateItem = async (newItem: CreateItemRequest) => {
    try {
      const createdItem = await createStoreItem(storeId, newItem);
      setItems(prev => [...prev, createdItem]);
    } catch (err) {
      console.error("상품 등록 실패:", err);
      setError("상품 등록에 실패했습니다.");
    }
  };

  const handleDeleteItem = async (itemId: number) => {
    try {
      await deleteStoreItem(storeId, itemId);
      setItems(prev => prev.filter(item => item.itemId !== itemId));
    } catch (err) {
      console.error("상품 삭제 실패:", err);
      setError("상품 삭제에 실패했습니다.");
    }
  };

  const renderTabContent = () => {
    if (error) return <p style={{ color: 'red' }}>{error}</p>;

    switch (activeTab) {
      case 'info':
        return (
          <div style={{ marginTop: '20px' }}>
            <h3 style={{ fontSize: '1.1rem', marginBottom: '10px' }}>가게 정보</h3>
            <p><strong>주소:</strong> {initialStoreData.address}</p>
            <p><strong>전화번호:</strong> {initialStoreData.phone_number}</p>
            <p><strong>설명:</strong> {initialStoreData.description}</p>
            <p><strong>카테고리:</strong> {initialStoreData.category}</p>
            <p><strong>운영 시간:</strong> {initialStoreData.startDate.hour}:{initialStoreData.startDate.minute} ~ {initialStoreData.endDate.hour}:{initialStoreData.endDate.minute}</p>
            {/* latitude와 longitude는 initialStoreData에 포함되어야 합니다. */}
            <p><strong>좌표:</strong> 위도 {initialStoreData.latitude}, 경도 {initialStoreData.longitude}</p>
          </div>
        );
      case 'items':
        return (
          <div style={{ marginTop: '20px' }}>
            <h3 style={{ fontSize: '1.1rem', marginBottom: '10px' }}>상품 목록</h3>
            {items.length === 0 ? (
              <p>등록된 상품이 없습니다.</p>
            ) : (
              <ul>
                {items.map(item => (
                  <li key={item.itemId} style={{ marginBottom: '10px' }}>
                    <strong>{item.name}</strong> - {item.price}원
                    <button 
                      onClick={() => handleDeleteItem(item.itemId)} 
                      style={{ marginLeft: '10px', color: 'red', cursor: 'pointer', backgroundColor: 'transparent', border: 'none' }}
                    >
                      삭제
                    </button>
                  </li>
                ))}
              </ul>
            )}
          </div>
        );
      case 'events':
        return (
          <div style={{ marginTop: '20px' }}>
            <h3 style={{ fontSize: '1.1rem', marginBottom: '10px' }}>이벤트 목록</h3>
            {events.length === 0 ? (
              <p>등록된 이벤트가 없습니다.</p>
            ) : (
              <ul>
                {events.map(event => (
                  <li key={event.id} style={{ marginBottom: '10px' }}>
                    <strong>{event.title}</strong> - {event.description}
                  </li>
                ))}
              </ul>
            )}
          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div>
      <div style={{ display: 'flex', borderBottom: '1px solid #ccc', marginBottom: '20px' }}>
        <Tab label="가게 정보" onClick={() => setActiveTab('info')} isActive={activeTab === 'info'} />
        <Tab label="상품 관리" onClick={() => setActiveTab('items')} isActive={activeTab === 'items'} />
        <Tab label="이벤트 관리" onClick={() => setActiveTab('events')} isActive={activeTab === 'events'} />
      </div>
      {renderTabContent()}
    </div>
  );
}
