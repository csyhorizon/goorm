'use client';

import { EventResponse } from '@/lib/apis/store.api';
import Link from 'next/link'; // Link 컴포넌트 import

// 백엔드의 날짜 배열을 JS Date 객체로 변환하는 헬퍼 함수
const createDateFromInput = (dateInput: any): Date | null => {
  if (Array.isArray(dateInput) && dateInput.length >= 3) {
    const [year, month, day, hours = 0, minutes = 0, seconds = 0] = dateInput;
    return new Date(year, month - 1, day, hours, minutes, seconds);
  }
  if (typeof dateInput === 'string') {
      const date = new Date(dateInput.replace(" ", "T"));
      if (!isNaN(date.getTime())) {
          return date;
      }
  }
  return null;
};

// 날짜 포맷팅 함수
const formatDate = (dateInput: any) => {
  const date = createDateFromInput(dateInput);
  if (!date) return '날짜 정보 없음';
  
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}.${month}.${day}`;
};

// 할인 정보를 텍스트로 변환하는 함수
const getDiscountText = (event: EventResponse) => {
  if (event.eventCategory === 'DISCOUNT_PERCENTAGE' && event.discountRate > 0) {
    return `${event.discountRate}% 할인`;
  }
  if (event.eventCategory === 'DISCOUNT_AMOUNT' && event.discountAmount > 0) {
    return `${event.discountAmount.toLocaleString()}원 할인`;
  }
  return null;
};

interface StoreNoticeListProps {
  events: EventResponse[];
}

export default function StoreNoticeList({ events }: StoreNoticeListProps) {
  const now = new Date();
  const activeEvents = events.filter(event => {
    const endTime = createDateFromInput(event.endTime);
    return endTime && endTime > now;
  });

  return (
    <section style={{ padding: '16px' }}>
      <h2 style={{ fontSize: '1.2rem', marginBottom: '16px' }}>진행중인 이벤트</h2>
      {activeEvents.length > 0 ? (
        <ul style={{ listStyle: 'none', margin: 0, padding: 0 }}>
          {activeEvents.map(event => {
            const discountText = getDiscountText(event);
            return (
              // [수정] li 전체를 Link로 감싸서 클릭 가능하게 만듭니다.
              <li key={event.id}>
                <Link href={`/events/${event.id}`} style={{ 
                    display: 'block',
                    padding: '12px 0', 
                    borderBottom: '1px solid #f2f3f5',
                    textDecoration: 'none',
                    color: 'inherit'
                }}>
                  <div>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                      {discountText && (
                        <span style={{
                          backgroundColor: '#e85757',
                          color: 'white',
                          padding: '2px 8px',
                          borderRadius: '12px',
                          fontSize: '0.8rem',
                          fontWeight: 'bold'
                        }}>
                          {discountText}
                        </span>
                      )}
                      <p style={{ margin: 0, fontWeight: '500', fontSize: '1rem' }}>{event.title}</p>
                    </div>
                    <p style={{ margin: '4px 0 0', fontSize: '0.9rem', color: '#555' }}>
                      {event.description}
                    </p>
                    <p style={{ margin: '8px 0 0', fontSize: '0.8rem', color: '#888' }}>
                      {formatDate(event.startTime)} ~ {formatDate(event.endTime)}
                    </p>
                  </div>
                </Link>
              </li>
            );
          })}
        </ul>
      ) : (
        <p>진행중인 이벤트가 없습니다.</p>
      )}
    </section>
  );
};
