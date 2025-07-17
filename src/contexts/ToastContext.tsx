'use client';

import { createContext, useState, ReactNode, useCallback, useEffect } from 'react';
import ToastModal from '@/components/common/ToastModal';

export interface Notification {
  id: number;
  message: string;
  redirectPath?: string;
  timestamp: string;
}

interface ToastContextType {
  notifications: Notification[];
  hasNew: boolean;
  showToast: (message: string, redirectPath?: string) => void;
  clearNotifications: () => void;
  markAsRead: () => void;
}

export const ToastContext = createContext<ToastContextType | undefined>(undefined);

export function ToastProvider({ children }: { children: ReactNode }) {
  const [toast, setToast] = useState<Omit<Notification, 'id' | 'timestamp'> | null>(null);
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [hasNew, setHasNew] = useState(false);

  useEffect(() => {
    if (!toast) return;
    const timer = setTimeout(() => setToast(null), 4000);
    return () => clearTimeout(timer);
  }, [toast]);

  const showToast = useCallback((message: string, redirectPath?: string) => {
    setToast({ message, redirectPath });
    setHasNew(true);
    const newNotification: Notification = {
      id: Date.now(),
      message,
      redirectPath,
      timestamp: '방금 전',
    };
    setNotifications(prev => [newNotification, ...prev]);
  }, []);

  const clearNotifications = useCallback(() => {
    setNotifications([]);
    setHasNew(false);
  }, []);

  const markAsRead = useCallback(() => {
    setHasNew(false);
  }, []);

  return (
    <ToastContext.Provider value={{ showToast, notifications, clearNotifications, hasNew, markAsRead }}>
      {children}
      {toast && (
        <ToastModal
          message={toast.message}
          redirectPath={toast.redirectPath}
          onClose={() => setToast(null)}
        />
      )}
    </ToastContext.Provider>
  );
}