'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import ProfileSection from './ProfileSection';
import NotificationSection from './NotificationSection';
import AccountSection from './AccountSection';

interface User {
  name: string;
  email: string;
  avatarUrl: string;
}
interface NotificationSettings {
  pushEnabled: boolean;
  emailEnabled: boolean;
}

export default function SettingsPage() {
  const [user, setUser] = useState<User | null>(null);
  const [settings, setSettings] = useState<NotificationSettings | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    setIsLoading(true);
    console.log('Fetching initial data...');
    setTimeout(() => {
      setUser({
        name: 'test',
        email: 'test@example.com',
        avatarUrl: 'https://via.placeholder.com/150',
      });
      setSettings({
        pushEnabled: true,
        emailEnabled: false,
      });
      setIsLoading(false);
      console.log('Initial data fetched.');
    }, 100);
  }, []);

  // 프로필 업데이트 API 호출
  const handleProfileUpdate = async (newName: string) => {
    if (!user) return;

    const updatedUser = { ...user, name: newName };
    setUser(updatedUser);

    console.log(`API CALL: Profile updating with new name: ${newName}`);
    // await fetch('/api/user/profile', { method: 'PUT', body: JSON.stringify({ name: newName }) });
  };

  // 알림 설정을 변경하는 API 호출
  const handleNotificationChange = async (type: 'push' | 'email', value: boolean) => {
    if (!settings) return;

    const newSettings = { ...settings, [type === 'push' ? 'pushEnabled' : 'emailEnabled']: value };
    setSettings(newSettings);
    
    console.log(`API CALL: Notification settings updating:`, newSettings);
    // await fetch('/api/settings/notifications', { method: 'POST', body: JSON.stringify(newSettings) });
  };

  // 로그아웃 API 호출
  const handleLogout = async () => {
    console.log('API CALL: Logging out...');
    // await fetch('/api/auth/logout', { method: 'POST' });
    alert('로그아웃 되었습니다.');
    router.push('/');
  };

  // 회원 탈퇴 API 호출
  const handleWithdrawal = async () => {
    if (confirm('모든 데이터가 삭제됩니다. 정말로 탈퇴하시겠습니까?')) {
      console.log('API CALL: Withdrawing account...');
      // await fetch('/api/auth/withdrawal', { method: 'POST' });
      alert('회원 탈퇴 처리되었습니다.');
      router.push('/');
    }
  };

  if (isLoading) {
    return <div style={{ textAlign: 'center', padding: '50px' }}>로딩 중...</div>;
  }

  return (
    <div style={{ maxWidth: '600px', margin: '40px auto', padding: '20px' }}>
      <h1 style={{ fontSize: '2rem', marginBottom: '40px', color: 'black' }}>설정</h1>
      
      <ProfileSection 
        user={user} 
        onUpdateProfile={handleProfileUpdate} 
      />
      
      <NotificationSection 
        settings={settings} 
        onSettingChange={handleNotificationChange} 
      />
      
      <AccountSection 
        onLogout={handleLogout} 
        onWithdrawal={handleWithdrawal} 
      />
    </div>
  );
}