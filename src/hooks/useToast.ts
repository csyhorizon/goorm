'use client';

import { useContext } from 'react';
import { ToastContext } from '@/contexts/ToastContext';

export const useToast = () => {
  const context = useContext(ToastContext);
  if (context === undefined) {
    throw new Error('useToast must be used within a ToastProvider');
  }
  return context;
};



  // const { showToast } = useToast();

  // const handleNotificationClick = () => {
  //   // 메시지와 함께 리다이렉트할 경로를 전달합니다.
  //   showToast('알림 내용', '/appointments/123');
  // };