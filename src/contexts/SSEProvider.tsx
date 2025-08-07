'use client';

import { useEffect } from 'react';
import { useAuth } from './AuthContext';
import { useToast } from './ToastContext';
import { subscribeToAlarms, AlarmResponse } from '@/lib/apis/alarm.api';

export function SSEProvider({ children }: { children: React.ReactNode }) {
  const { isLoggedIn, user } = useAuth();
  const { showToast } = useToast();

  useEffect(() => {
    let eventSource: EventSource | undefined;

    if (isLoggedIn && user) {
      eventSource = subscribeToAlarms();

      // 'alarm'이라는 이름의 이벤트를 수신
      eventSource.addEventListener('alarm', (event) => {
        try {
          const newAlarm: AlarmResponse = JSON.parse(event.data);
          if (newAlarm.content) {
            showToast(newAlarm);
          }
        } catch (error) {
          console.error('Failed to parse SSE event data:', error);
        }
      });
    }

    return () => {
      if (eventSource) {
        eventSource.close();
        console.log('SSE connection closed.');
      }
    };
  }, [isLoggedIn, user, showToast]);

  return <>{children}</>;
}