'use client';

import { useState, useEffect } from 'react';
import { getMyProfile, MemberResponse } from '@/lib/apis/member.api';
import { getMyStore, StoreResponse } from '@/lib/apis/store.api';
import Link from 'next/link';

const roleMapping = {
  'USER': '일반 사용자',
  'ADMIN': '관리자',
  'OWNER': '사업자',
};

export default function ProfileSection() {
  const [user, setUser] = useState<MemberResponse | null>(null);
  const [store, setStore] = useState<StoreResponse | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const profileData = await getMyProfile();
        setUser(profileData);

        if (profileData.role === 'OWNER') {
          try {
            const storeData = await getMyStore();
            setStore(storeData);
          } catch {
            console.warn("사장님의 가게 정보가 없습니다.");
          }
        }
      } catch (err) {
        console.error("프로필 정보를 불러오는 데 실패했습니다:", err);
        setError("프로필 정보를 불러오는 데 실패했습니다.");
      } finally {
        setIsLoading(false);
      }
    };

    fetchProfileData();
  }, []);

  if (isLoading) {
    return (
      <section style={{ marginBottom: '40px' }}>
        <p>프로필 정보를 불러오는 중...</p>
      </section>
    );
  }

  if (error) {
    return (
      <section style={{ marginBottom: '40px' }}>
        <p style={{ color: 'red' }}>{error}</p>
      </section>
    );
  }

  if (!user) {
    return null;
  }

  const userRoleText = roleMapping[user.role as keyof typeof roleMapping] || user.role;

  return (
    <section style={{ marginBottom: '40px' }}>
      <div style={{ borderBottom: '1px solid #eee', paddingBottom: '10px' }}>
        <h2 style={{ fontSize: '1.2rem', margin: 0, color: 'black' }}>
          프로필 정보
        </h2>
      </div>

      <div style={{ padding: '20px 0' }}>
        <p style={{ margin: 0, fontSize: '1.5rem', fontWeight: 'bold', color: 'black' }}>
          {user.username}
        </p>
        <p style={{ margin: '5px 0 0', color: '#555' }}>
          {user.email}
        </p>
        <p style={{ margin: '5px 0 0', color: '#555' }}>
          역할: {userRoleText}
        </p>
      </div>

      {user.role === 'OWNER' && store && (
        <div style={{ marginTop: '20px', paddingTop: '20px', borderTop: '1px solid #eee' }}>
          <Link href={`/stores/${store.id}/manage`} passHref>
            <button style={{
              padding: '10px 20px',
              backgroundColor: '#4a90e2',
              color: 'white',
              border: 'none',
              borderRadius: '5px',
              cursor: 'pointer'
            }}>
              가게 관리하기
            </button>
          </Link>
        </div>
      )}
    </section>
  );
}