import React from 'react';
import { useGetProfileQuery } from '@/lib/api';
import { ProfileHeader } from '@/components/ProfileHeader';
import { ProfileGrid } from '@/components/ProfileGrid';
import { ProfileTabs } from '@/components/ProfileTabs';

export default function MyProfilePage() {
  const { data: user, isLoading } = useGetProfileQuery();

  // 🧪 더미 사용자 데이터 (백엔드 없을 때)
  const dummyUser = {
    id: 1,
    username: 'dummy-user',
    email: 'dummy@example.com',
    profileImage: 'https://via.placeholder.com/150x150/4ECDC4/FFFFFF?text=DUMMY'
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-gray-900"></div>
      </div>
    );
  }

  // 백엔드 실패 시 더미 데이터 사용
  const displayUser = user || dummyUser;

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-4xl mx-auto">
        <ProfileHeader user={displayUser} />
        <ProfileTabs />
        <ProfileGrid userId={displayUser.id} />
      </div>
    </div>
  );
} 