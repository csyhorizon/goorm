'use client';

import { useState, useEffect, useRef } from 'react';
import { useRouter } from 'next/navigation';
import { useToast } from '@/hooks/useToast';

const BellIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="black" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path><path d="M13.73 21a2 2 0 0 1-3.46 0"></path></svg>
);

export default function NotificationBell() {
  const [isOpen, setIsOpen] = useState(false);
  const { notifications, clearNotifications, hasNew, markAsRead } = useToast();
  const router = useRouter();
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setIsOpen(false);
      }
    }
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, [dropdownRef]);
  
  const handleBellClick = () => {
    setIsOpen(!isOpen);
    if (!isOpen) {
      markAsRead(); // 드롭다운을 열 때 '읽음'으로 처리
    }
  };

  const handleNotificationClick = (path?: string) => {
    if (path) {
      router.push(path);
    }
    setIsOpen(false);
  };

  return (
    <div ref={dropdownRef} style={{ position: 'relative', zIndex: 1001 }}>
      <button onClick={handleBellClick} style={{ position: 'relative', background: 'none', border: 'none', cursor: 'pointer', padding: '8px' }}>
        <BellIcon />
        {/* 새 알림이 있을 때 빨간 점 표시 */}
        {hasNew && (
          <span style={{
            position: 'absolute',
            top: '8px',
            right: '8px',
            width: '8px',
            height: '8px',
            backgroundColor: 'red',
            borderRadius: '50%',
          }}></span>
        )}
      </button>

      {isOpen && (
        <div style={{ position: 'absolute', top: '40px', right: 0, width: '300px', backgroundColor: 'white', borderRadius: '8px', boxShadow: '0 4px 12px rgba(0,0,0,0.15)', border: '1px solid #eaeaea' }}>
          <div style={{ padding: '12px 16px', borderBottom: '1px solid #eaeaea', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <h4 style={{ margin: 0, fontSize: '1rem', color: 'black' }}>알림</h4>
            <button onClick={clearNotifications} style={{ fontSize: '0.8rem', color: '#007bff', background: 'none', border: 'none', cursor: 'pointer' }}>
              전체 읽음
            </button>
          </div>
          <ul style={{ listStyle: 'none', margin: 0, padding: 0, maxHeight: '400px', overflowY: 'auto' }}>
            {notifications.length > 0 ? (
              notifications.map((notif) => (
                <li key={notif.id} onClick={() => handleNotificationClick(notif.redirectPath)} style={{ padding: '12px 16px', borderBottom: '1px solid #eaeaea', cursor: 'pointer' }}>
                  <p style={{ margin: 0, fontSize: '0.9rem', color: 'black' }}>{notif.message}</p>
                  <p style={{ margin: '4px 0 0', fontSize: '0.75rem', color: '#888' }}>{notif.timestamp}</p>
                </li>
              ))
            ) : (
              <li style={{ padding: '20px 16px', textAlign: 'center', color: '#888' }}>새로운 알림이 없습니다.</li>
            )}
          </ul>
        </div>
      )}
    </div>
  );
}