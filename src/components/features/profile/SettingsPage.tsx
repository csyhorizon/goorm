'use client';

import { useRouter } from 'next/navigation';
import { useEffect } from 'react';
import { useAuth } from '@/contexts/AuthContext'; // 1. AuthContext 훅 import
import ProfileSection from './ProfileSection';
import AccountSection from './AccountSection';

export default function SettingsPage() {
  const { user, loading, deleteLocalData } = useAuth();
  const router = useRouter();

  async function handleLogout() {
    try {
      await fetch('/api/auth/logout', { method: 'POST' });
    } catch (error) {
      console.error('로그아웃 중 오류 발생:', error);
    } finally {
      deleteLocalData();
    }
  }

  useEffect(() => {
    if (!loading && !user) {
      router.push('/auth/login');
    }
  }, [user, loading, router]);

  if (loading || !user) {
    return <div style={{ textAlign: 'center', padding: '50px' }}>로딩 중...</div>;
  }

  return (
    <div style={{ maxWidth: '600px', margin: '40px auto', padding: '20px' }}>
      <h1 style={{ fontSize: '2rem', marginBottom: '40px', color: 'black' }}>설정</h1>

      <ProfileSection
      />

      <AccountSection
        onLogout={handleLogout}
      />
    </div>
  );
}