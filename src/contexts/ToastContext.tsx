'use client';

import { createContext, useState, ReactNode, useCallback, useEffect, useContext } from 'react';
import ToastModal from '@/components/common/ToastModal';
import { AlarmResponse } from '@/lib/apis/alarm.api';

interface ToastContextType {
  notifications: AlarmResponse[];
  hasNew: boolean;
  showToast: (alarm: AlarmResponse) => void;
  setInitialAlarms: (alarms: AlarmResponse[]) => void;
  setHasNew: (hasNew: boolean) => void;
  removeNotification: (id: number) => void;
}

export const ToastContext = createContext<ToastContextType | undefined>(undefined);

export function ToastProvider({ children }: { children: ReactNode }) {
  const [toast, setToast] = useState<{ message: string, redirectPath?: string } | null>(null);
  const [notifications, setNotifications] = useState<AlarmResponse[]>([]);
  const [hasNew, setHasNew] = useState(false);

  useEffect(() => {
    if (!toast) return;
    const timer = setTimeout(() => setToast(null), 4000);
    return () => clearTimeout(timer);
  }, [toast]);

  const showToast = useCallback((alarm: AlarmResponse) => {
    setToast({ message: alarm.content, redirectPath: alarm.targetUrl });
    setNotifications(prev => [alarm, ...prev].sort((a, b) => b.id - a.id));
    setHasNew(true);
  }, []);

  const setInitialAlarms = useCallback((alarms: AlarmResponse[]) => {
    setNotifications(alarms);
  }, []);

  const removeNotification = useCallback((id: number) => {
    setNotifications(prev => prev.filter(n => n.id !== id));
  }, []);

  return (
    <ToastContext.Provider value={{ notifications, hasNew, showToast, setInitialAlarms, setHasNew, removeNotification }}>
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

export const useToast = () => {
    const context = useContext(ToastContext);
    if (!context) {
        throw new Error('useToast must be used within a ToastProvider');
    }
    return context;
}