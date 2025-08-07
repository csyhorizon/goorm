'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/contexts/AuthContext';
import { useToast } from '@/contexts/ToastContext';
import { getAlarms, getUnreadAlarmCount, readAllAlarms, deleteAlarm, AlarmResponse } from '@/lib/apis/alarm.api';

// --- Helper Functions ---
const formatDate = (dateInput: any) => {
    if (!dateInput) return '';
    if (Array.isArray(dateInput)) {
        const [year, month, day] = dateInput;
        return `${year}.${String(month).padStart(2, '0')}.${String(day).padStart(2, '0')}`;
    }
    if (typeof dateInput === 'string') {
        const date = new Date(dateInput);
        if (!isNaN(date.getTime())) return date.toLocaleString('ko-KR');
    }
    return '날짜 정보 없음';
};

const parseRedirectUrl = (targetUrl?: string): string | null => {
    if (!targetUrl) return null;
    const eventMatch = targetUrl.match(/\/event\/(\d+)/);
    if (eventMatch && eventMatch[1]) {
        return `/events/${eventMatch[1]}`;
    }
    // TODO: 다른 종류의 알림 URL 파싱 규칙 추가 (예: 게시글)
    return null;
};

// --- UI Components ---
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
  const { isLoggedIn } = useAuth();
  const { notifications, hasNew, setHasNew, setInitialAlarms, removeNotification } = useToast();
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter();

  // 주기적으로 안 읽은 알림 개수 확인
  useEffect(() => {
    if (!isLoggedIn) return;
    const checkUnread = async () => {
        try {
            const count = await getUnreadAlarmCount();
            setHasNew(count > 0);
        } catch (error) {
            console.error("Failed to get unread count:", error);
        }
    };
    checkUnread();
    const interval = setInterval(checkUnread, 60000); // 1분마다 확인
    return () => clearInterval(interval);
  }, [isLoggedIn, setHasNew]);

  const handleBellClick = async () => {
    if (!isLoggedIn) return;
    
    setIsOpen(prev => !prev);
    
    if (!isOpen) {
      setIsLoading(true);
      try {
        const fetchedAlarms = await getAlarms();
        setInitialAlarms(fetchedAlarms);
        if (hasNew) {
          await readAllAlarms();
          setHasNew(false);
        }
      } catch (error) {
        console.error('Failed to fetch alarms:', error);
      } finally {
        setIsLoading(false);
      }
    }
  };

  const handleDelete = async (e: React.MouseEvent, alarmId: number) => {
    e.stopPropagation();
    try {
      await deleteAlarm(alarmId);
      removeNotification(alarmId);
    } catch (error) {
      console.error('Failed to delete alarm:', error);
    }
  };

  const handleNotificationClick = (alarm: AlarmResponse) => {
    const path = parseRedirectUrl(alarm.targetUrl);
    if (path) {
        router.push(path);
        setIsOpen(false);
    }
  };

  if (!isLoggedIn) return null;

  return (
    <div style={{ position: 'relative' }}>
      <div onClick={handleBellClick}>
        <BellIcon hasNew={hasNew} />
      </div>

      {isOpen && (
        <div style={{
          position: 'absolute', top: '40px', right: 0, width: '320px',
          maxHeight: '400px', overflowY: 'auto', backgroundColor: 'white',
          borderRadius: '8px', boxShadow: '0 4px 12px rgba(0,0,0,0.15)',
          border: '1px solid #eee', zIndex: 1001,
        }}>
          <div style={{ padding: '16px', borderBottom: '1px solid #eee' }}>
            <h4 style={{ margin: 0 }}>알림</h4>
          </div>
          {isLoading ? (
            <div style={{ padding: '20px', textAlign: 'center' }}>로딩 중...</div>
          ) : notifications.length > 0 ? (
            notifications.map(alarm => (
              <div key={alarm.id} onClick={() => handleNotificationClick(alarm)} style={{ cursor: 'pointer' }}>
                <div style={{ display: 'flex', alignItems: 'center', padding: '12px 16px', borderBottom: '1px solid #eee' }}>
                  <div style={{ flex: 1 }}>
                    <p style={{ margin: 0, fontSize: '0.9rem' }}>{alarm.content}</p>
                    <p style={{ margin: '4px 0 0', fontSize: '0.75rem', color: '#888' }}>{formatDate(alarm.createdAt)}</p>
                  </div>
                  <button onClick={(e) => handleDelete(e, alarm.id)} style={{ background: 'none', border: 'none', cursor: 'pointer', fontSize: '1.2rem', color: '#aaa' }}>×</button>
                </div>
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