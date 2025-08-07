'use client';

import { EventResponse } from '@/lib/apis/store.api';

// [수정] 백엔드의 날짜 배열을 JS Date 객체로 변환하는 헬퍼 함수
const createDateFromInput = (dateInput: any): Date | null => {
  // 1. 입력값이 숫자 배열인지 확인합니다. (예: [2025, 8, 7, ...])
  if (Array.isArray(dateInput) && dateInput.length >= 3) {
    const [year, month, day, hours = 0, minutes = 0, seconds = 0] = dateInput;
    // 자바스크립트의 월은 0부터 시작하므로, 서버에서 받은 월(1~12)에서 1을 빼줍니다.
    return new Date(year, month - 1, day, hours, minutes, seconds);
  }

  // 2. 만약 배열이 아닌 문자열 형식일 경우를 대비한 방어 코드
  if (typeof dateInput === 'string') {
      const date = new Date(dateInput.replace(" ", "T"));
      if (!isNaN(date.getTime())) {
          return date;
      }
  }

  // 유효하지 않은 형식이면 null을 반환합니다.
  return null;
};


// 날짜 포맷팅 함수
const formatDate = (dateInput: any) => {
  const date = createDateFromInput(dateInput);
  if (!date) return '날짜 정보 없음'; // Date 객체 생성 실패 시
  
  // 'YYYY.MM.DD' 형식으로 변환합니다.
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
  // [추가] 현재 시간을 기준으로 종료된 이벤트를 필터링합니다.
  const now = new Date();
  const activeEvents = events.filter(event => {
    const endTime = createDateFromInput(event.endTime);
    // endTime이 유효하고, 현재 시간보다 미래일 경우에만 true를 반환합니다.
    return endTime && endTime > now;
  });

  return (
    <section style={{ padding: '16px' }}>
      <h2 style={{ fontSize: '1.2rem', marginBottom: '16px' }}>진행중인 이벤트</h2>
      {/* [수정] 필터링된 activeEvents 배열을 사용합니다. */}
      {activeEvents.length > 0 ? (
        <ul style={{ listStyle: 'none', margin: 0, padding: 0 }}>
          {activeEvents.map(event => {
            const discountText = getDiscountText(event);
            return (
              <li key={event.id} style={{ padding: '12px 0', borderBottom: '1px solid #f2f3f5' }}>
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
