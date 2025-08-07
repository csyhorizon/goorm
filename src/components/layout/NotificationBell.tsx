'use client';

import { useState, useEffect, useContext } from 'react';
import { useAuth } from '@/contexts/AuthContext';
import { ToastContext } from '@/contexts/ToastContext';
import { getAlarms, readAllAlarms, deleteAlarm, AlarmResponse } from '@/lib/apis/alarm.api';

// 벨 아이콘 SVG
const BellIcon = ({ hasNew }: { hasNew: boolean }) => (
  <div style={{ position: 'relative', cursor: 'pointer' }}>
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
      <path d="M13.73 21a2 2 0 0 1-3.46 0" />
    </svg>
    {hasNew && <div style={{ position: 'absolute', top: '0', right: '0', width: '8px', height: '8px', borderRadius: '50%', backgroundColor: 'red' }} />}
  </div>
);

export default function NotificationBell() {
  const { user, isLoggedIn } = useAuth();
  const toastContext = useContext(ToastContext);
  const [isOpen, setIsOpen] = useState(false);
  const [alarms, setAlarms] = useState<AlarmResponse[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  // 벨을 클릭했을 때의 동작
  const handleBellClick = async () => {
    if (!isLoggedIn || !user) return;
    
    setIsOpen(prev => !prev);
    
    // 드롭다운이 열릴 때, 알림 목록을 불러오고 '읽음' 처리
    if (!isOpen) {
      setIsLoading(true);
      try {
        const fetchedAlarms = await getAlarms(user.id);
        setAlarms(fetchedAlarms);
        // 안 읽은 알림이 있을 때만 '모두 읽음' API 호출
        if (toastContext?.hasNew) {
          await readAllAlarms(user.id);
          toastContext.markAsRead();
        }
      } catch (error) {
        console.error('Failed to fetch alarms:', error);
      } finally {
        setIsLoading(false);
      }
    }
  };

  // 특정 알림 삭제
  const handleDelete = async (alarmId: number) => {
    try {
      await deleteAlarm(alarmId);
      setAlarms(prev => prev.filter(alarm => alarm.id !== alarmId));
    } catch (error) {
      console.error('Failed to delete alarm:', error);
    }
  };

  if (!isLoggedIn) return null;

  return (
    <div style={{ position: 'relative' }}>
      <div onClick={handleBellClick}>
        <BellIcon hasNew={toastContext?.hasNew || false} />
      </div>

      {isOpen && (
        <div style={{
          position: 'absolute',
          top: '40px',
          right: 0,
          width: '300px',
          maxHeight: '400px',
          overflowY: 'auto',
          backgroundColor: 'white',
          borderRadius: '8px',
          boxShadow: '0 4px 12px rgba(0,0,0,0.15)',
          border: '1px solid #eee',
          zIndex: 1001,
        }}>
          <div style={{ padding: '16px', borderBottom: '1px solid #eee' }}>
            <h4 style={{ margin: 0 }}>알림</h4>
          </div>
          {isLoading ? (
            <div style={{ padding: '20px', textAlign: 'center' }}>로딩 중...</div>
          ) : alarms.length > 0 ? (
            alarms.map(alarm => (
              <div key={alarm.id} style={{ display: 'flex', alignItems: 'center', padding: '12px 16px', borderBottom: '1px solid #eee' }}>
                <div style={{ flex: 1 }}>
                  <p style={{ margin: 0, fontSize: '0.9rem' }}>{alarm.content}</p>
                  <p style={{ margin: '4px 0 0', fontSize: '0.75rem', color: '#888' }}>
                    {new Date(alarm.createdAt).toLocaleString()}
                  </p>
                </div>
                <button onClick={() => handleDelete(alarm.id)} style={{ background: 'none', border: 'none', cursor: 'pointer', fontSize: '1rem' }}>×</button>
              </div>
            ))
          ) : (
            <div style={{ padding: '20px', textAlign: 'center', color: '#888' }}>알림이 없습니다.</div>
          )}
        </div>
      )}
    </div>
  );
}
