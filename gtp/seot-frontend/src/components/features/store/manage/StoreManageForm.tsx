'use client';

import { useState, useEffect } from 'react';
import { 
  StoreResponse, 
  ItemResponse, 
  EventResponse, 
  deleteStoreItem,
  createStoreItem,
  createStoreEvent,
  CreateItemRequest,
  CreateEventRequest,
} from '@/lib/apis/store.api';
import { deleteEvent } from '@/lib/apis/event.api';

declare global {
  interface Window {
    kakao: any;
  }
}

const StoreMap = ({ latitude, longitude }: { latitude: number; longitude: number; }) => {
  useEffect(() => {
    if (window.kakao && window.kakao.maps) {
      window.kakao.maps.load(() => {
        const mapContainer = document.getElementById('map');
        if (!mapContainer) return;
        const mapOption = {
          center: new window.kakao.maps.LatLng(latitude, longitude),
          level: 3,
        };
        const map = new window.kakao.maps.Map(mapContainer, mapOption);
        const markerPosition = new window.kakao.maps.LatLng(latitude, longitude);
        const marker = new window.kakao.maps.Marker({
          position: markerPosition,
        });
        marker.setMap(map);
      });
    }
  }, [latitude, longitude]);

  return <div id="map" style={{ width: '100%', height: '300px', borderRadius: '8px', marginTop: '10px' }}></div>;
};


interface TabProps {
  label: string;
  onClick: () => void;
  isActive: boolean;
}

const Tab = ({ label, onClick, isActive }: TabProps) => (
  <button
    onClick={onClick}
    style={{
      padding: '10px 20px',
      border: 'none',
      borderBottom: isActive ? '2px solid #4a90e2' : '2px solid transparent',
      color: isActive ? '#4a90e2' : '#555',
      fontWeight: isActive ? 'bold' : 'normal',
      cursor: 'pointer',
      backgroundColor: 'transparent',
      fontSize: '1rem'
    }}
  >
    {label}
  </button>
);

const formStyles = {
  input: {
    width: '100%',
    padding: '10px',
    border: '1px solid #ccc',
    borderRadius: '4px',
    boxSizing: 'border-box',
  },
  textarea: {
    width: '100%',
    padding: '10px',
    border: '1px solid #ccc',
    borderRadius: '4px',
    minHeight: '80px',
    boxSizing: 'border-box',
  },
  label: {
    display: 'block',
    marginBottom: '5px',
    fontWeight: '500'
  },
  button: {
    padding: '10px 15px',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontWeight: 'bold',
  },
  primaryButton: {
    backgroundColor: '#4a90e2',
    color: 'white',
  },
  secondaryButton: {
    backgroundColor: '#f0f0f0',
    color: '#333',
  }
} as const;

const AddItemForm = ({ storeId, onItemAdded, onCancel }: { storeId: number; onItemAdded: (newItem: ItemResponse) => void; onCancel: () => void; }) => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState(0);
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!name || price <= 0) {
      setError('상품명과 가격을 올바르게 입력해주세요.');
      return;
    }
    const itemData: CreateItemRequest = { name, description, price };
    try {
      const newItem = await createStoreItem(storeId, itemData);
      onItemAdded(newItem);
    } catch (err) {
      setError('상품 등록에 실패했습니다.');
      console.error(err);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ padding: '20px', border: '1px solid #eee', borderRadius: '8px', marginTop: '20px' }}>
      <h4 style={{ marginTop: 0, marginBottom: '20px' }}>새 상품 추가</h4>
      <div style={{ marginBottom: '10px' }}>
        <label style={formStyles.label}>상품명</label>
        <input type="text" value={name} onChange={e => setName(e.target.value)} style={formStyles.input} />
      </div>
      <div style={{ marginBottom: '10px' }}>
        <label style={formStyles.label}>설명</label>
        <textarea value={description} onChange={e => setDescription(e.target.value)} style={formStyles.textarea} />
      </div>
      <div style={{ marginBottom: '20px' }}>
        <label style={formStyles.label}>가격</label>
        <input type="number" value={price} onChange={e => setPrice(Number(e.target.value))} style={formStyles.input} />
      </div>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <div style={{ display: 'flex', gap: '10px' }}>
        <button type="submit" style={{...formStyles.button, ...formStyles.primaryButton}}>등록</button>
        <button type="button" onClick={onCancel} style={{...formStyles.button, ...formStyles.secondaryButton}}>취소</button>
      </div>
    </form>
  );
};

const AddEventForm = ({ storeId, onEventAdded, onCancel }: { storeId: number; onEventAdded: (newEvent: EventResponse) => void; onCancel: () => void; }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [eventCategory, setEventCategory] = useState<'DISCOUNT_PERCENTAGE' | 'DISCOUNT_AMOUNT'>('DISCOUNT_AMOUNT');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [discountRate, setDiscountRate] = useState(0);
    const [discountAmount, setDiscountAmount] = useState(0);
    const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!title || !startTime || !endTime) {
        setError('이벤트명과 기간을 올바르게 입력해주세요.');
        return;
    }
    const eventData: CreateEventRequest = { title, description, eventCategory, startTime: new Date(startTime).toISOString(), endTime: new Date(endTime).toISOString(), discountRate, discountAmount };
    try {
      const newEvent = await createStoreEvent(storeId, eventData);
      onEventAdded(newEvent);
    } catch (err) {
      setError('이벤트 등록에 실패했습니다.');
      console.error(err);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ padding: '20px', border: '1px solid #eee', borderRadius: '8px', marginTop: '20px' }}>
      <h4 style={{ marginTop: 0 }}>새 이벤트 추가</h4>
      <div style={{ marginBottom: '10px' }}><label style={formStyles.label}>이벤트명</label><input type="text" value={title} onChange={e => setTitle(e.target.value)} style={formStyles.input} /></div>
      <div style={{ marginBottom: '10px' }}><label style={formStyles.label}>설명</label><textarea value={description} onChange={e => setDescription(e.target.value)} style={formStyles.textarea} /></div>
      <div style={{ marginBottom: '10px' }}><label style={formStyles.label}>시작일시</label><input type="datetime-local" value={startTime} onChange={e => setStartTime(e.target.value)} style={formStyles.input} /></div>
      <div style={{ marginBottom: '10px' }}><label style={formStyles.label}>종료일시</label><input type="datetime-local" value={endTime} onChange={e => setEndTime(e.target.value)} style={formStyles.input} /></div>
      <div style={{ marginBottom: '10px' }}>
        <label style={formStyles.label}>할인 종류</label>
        <select value={eventCategory} onChange={e => setEventCategory(e.target.value as any)} style={formStyles.input}>
          <option value="DISCOUNT_AMOUNT">금액 할인</option>
          <option value="DISCOUNT_PERCENTAGE">비율 할인 (%)</option>
        </select>
      </div>
      {eventCategory === 'DISCOUNT_AMOUNT' ? (
        <div style={{ marginBottom: '20px' }}><label style={formStyles.label}>할인 금액 (원)</label><input type="number" value={discountAmount} onChange={e => setDiscountAmount(Number(e.target.value))} style={formStyles.input} /></div>
      ) : (
        <div style={{ marginBottom: '20px' }}><label style={formStyles.label}>할인율 (%)</label><input type="number" value={discountRate} onChange={e => setDiscountRate(Number(e.target.value))} style={formStyles.input} /></div>
      )}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <div style={{ display: 'flex', gap: '10px' }}>
        <button type="submit" style={{...formStyles.button, ...formStyles.primaryButton}}>등록</button>
        <button type="button" onClick={onCancel} style={{...formStyles.button, ...formStyles.secondaryButton}}>취소</button>
      </div>
    </form>
  );
};


interface StoreManageFormProps {
  storeId: number;
  initialStoreData: StoreResponse;
  initialItems: ItemResponse[];
  initialEvents: EventResponse[];
}

export default function StoreManageForm({ storeId, initialStoreData, initialItems, initialEvents }: StoreManageFormProps) {
  const [activeTab, setActiveTab] = useState('info');
  const [items, setItems] = useState<ItemResponse[]>(initialItems);
  const [events, setEvents] = useState<EventResponse[]>(initialEvents);
  const [error, setError] = useState<string | null>(null);
  
  const [showAddItemForm, setShowAddItemForm] = useState(false);
  const [showAddEventForm, setShowAddEventForm] = useState(false);

  const handleDeleteItem = async (itemId: number) => {
    if (!window.confirm("정말로 이 상품을 삭제하시겠습니까?")) return;
    try {
      await deleteStoreItem(storeId, itemId);
      setItems(prev => prev.filter(item => item.itemId !== itemId));
    } catch (err) {
      console.error("상품 삭제 실패:", err);
      setError("상품 삭제에 실패했습니다.");
    }
  };

  const handleDeleteEvent = async (eventId: number) => {
    if (!window.confirm("정말로 이 이벤트를 삭제하시겠습니까?")) return;
    try {
      await deleteEvent(eventId);
      setEvents(prev => prev.filter(event => event.id !== eventId));
    } catch (err) {
      console.error("이벤트 삭제 실패:", err);
      setError("이벤트 삭제에 실패했습니다.");
    }
  };

  const handleItemAdded = (newItem: ItemResponse) => {
    setItems(prev => [...prev, newItem]);
    setShowAddItemForm(false);
  };

  const handleEventAdded = (newEvent: EventResponse) => {
    setEvents(prev => [...prev, newEvent]);
    setShowAddEventForm(false);
  };

  const renderTabContent = () => {
    if (error) return <p style={{ color: 'red' }}>{error}</p>;

    switch (activeTab) {
      case 'info':
        if (!initialStoreData) {
          return <p>가게 정보를 불러올 수 없습니다.</p>;
        }
        const { address, phone_number, description, category, startDate, endDate, latitude, longitude } = initialStoreData;
        const formatTime = (time: any) => {
          if (Array.isArray(time) && time.length >= 2) {
            const [hours, minutes] = time;
            return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`;
          }
          if (typeof time === 'object' && time !== null && 'hour' in time && 'minute' in time) {
            return `${String(time.hour).padStart(2, '0')}:${String(time.minute).padStart(2, '0')}`;
          }
          if (typeof time === 'string' && time.includes(':')) {
            const parts = time.split(':');
            return `${parts[0]}:${parts[1]}`;
          }
          return '미정';
        };
        return (
          <div style={{ marginTop: '20px' }}>
            <div style={{ marginBottom: '20px' }}>
                <h3 style={{ fontSize: '1.1rem', marginBottom: '10px' }}>가게 정보</h3>
                <p><strong>주소:</strong> {address}</p>
                <p><strong>전화번호:</strong> {phone_number}</p>
                <p><strong>설명:</strong> {description}</p>
                <p><strong>카테고리:</strong> {category}</p>
                <p><strong>운영 시간:</strong> {formatTime(startDate)} ~ {formatTime(endDate)}</p>
            </div>
            <div>
                <h3 style={{ fontSize: '1.1rem', marginBottom: '10px' }}>가게 위치</h3>
                {typeof latitude === 'number' && typeof longitude === 'number' ? (
                  <StoreMap latitude={latitude} longitude={longitude} />
                ) : (
                  <p>위치 정보가 등록되지 않았습니다.</p>
                )}
            </div>
          </div>
        );
      case 'items':
        return (
          <div style={{ marginTop: '20px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3 style={{ fontSize: '1.1rem', marginBottom: '10px' }}>상품 목록</h3>
                {!showAddItemForm && <button onClick={() => setShowAddItemForm(true)} style={{...formStyles.button, ...formStyles.primaryButton}}>상품 추가</button>}
            </div>
            {showAddItemForm && <AddItemForm storeId={storeId} onItemAdded={handleItemAdded} onCancel={() => setShowAddItemForm(false)} />}
            
            {items.length === 0 ? (
              <p style={{ marginTop: '20px' }}>등록된 상품이 없습니다.</p>
            ) : (
              <ul style={{ listStyle: 'none', padding: 0, marginTop: '20px' }}>
                {items.map(item => (
                  <li key={item.itemId} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px', borderBottom: '1px solid #eee' }}>
                    <span><strong>{item.name}</strong> - {item.price.toLocaleString()}원</span>
                    <button onClick={() => handleDeleteItem(item.itemId)} style={{ color: 'red', cursor: 'pointer', backgroundColor: 'transparent', border: 'none' }}>삭제</button>
                  </li>
                ))}
              </ul>
            )}
          </div>
        );
      case 'events':
        return (
          <div style={{ marginTop: '20px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3 style={{ fontSize: '1.1rem', marginBottom: '10px' }}>이벤트 목록</h3>
                {!showAddEventForm && <button onClick={() => setShowAddEventForm(true)} style={{...formStyles.button, ...formStyles.primaryButton}}>이벤트 추가</button>}
            </div>
            {showAddEventForm && <AddEventForm storeId={storeId} onEventAdded={handleEventAdded} onCancel={() => setShowAddEventForm(false)} />}

            {events.length === 0 ? (
              <p style={{ marginTop: '20px' }}>등록된 이벤트가 없습니다.</p>
            ) : (
              <ul style={{ listStyle: 'none', padding: 0, marginTop: '20px' }}>
                {events.map((event: EventResponse) => (
                  <li key={event.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px', borderBottom: '1px solid #eee' }}>
                    <div>
                      <strong>{event.title}</strong>
                      <p style={{ margin: '5px 0', fontSize: '0.9rem', color: '#555' }}>{event.description}</p>
                    </div>
                    {/* [추가] 이벤트 삭제 버튼 */}
                    <button onClick={() => handleDeleteEvent(event.id)} style={{ color: 'red', cursor: 'pointer', backgroundColor: 'transparent', border: 'none' }}>삭제</button>
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
