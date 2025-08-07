'use client';

import { useEffect, useContext } from 'react';
import { useAuth } from './AuthContext';
import { ToastContext } from './ToastContext';
import { subscribeToAlarms } from '@/lib/apis/alarm.api';

// 이 컴포넌트는 UI가 없으며, 오직 SSE 연결만 관리합니다.
export function SSEProvider({ children }: { children: React.ReactNode }) {
  const { isLoggedIn, user } = useAuth();
  const toastContext = useContext(ToastContext);

  useEffect(() => {
    let eventSource: EventSource | undefined;

    // 로그인 상태이고, 사용자 정보와 토스트 컨텍스트가 있을 때만 SSE 구독
    if (isLoggedIn && user && toastContext) {
      const accessToken = localStorage.getItem('accessToken');
      if (accessToken) {
        eventSource = subscribeToAlarms(user.id, accessToken);

        // 서버로부터 새 알림 메시지를 받았을 때의 처리
        eventSource.onmessage = (event) => {
          try {
            const newAlarm = JSON.parse(event.data);
            // Toast 팝업을 띄우고 알림 목록에 추가
            if (newAlarm.content) {
              toastContext.showToast(newAlarm.content);
            }
          } catch (error) {
            console.error('Failed to parse SSE event data:', error);
          }
        };
      }
    }

    // 컴포넌트가 언마운트되거나, 로그아웃 상태가 되면 연결을 끊습니다.
    return () => {
      if (eventSource) {
        eventSource.close();
        console.log('SSE connection closed.');
      }
    };
  }, [isLoggedIn, user, toastContext]);

  return <>{children}</>;
}
