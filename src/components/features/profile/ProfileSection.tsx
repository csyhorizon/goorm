'use client';

import { useState, useEffect } from 'react';

interface User {
  name: string;
  email: string;
  avatarUrl: string;
}

const UserAvatar = ({ url }: { url: string }) => (
  <img src={url} alt="Profile Avatar" style={{ width: '80px', height: '80px', borderRadius: '50%', objectFit: 'cover' }} />
);

interface ProfileSectionProps {
  user: User | null;
  onUpdateProfile: (newName: string) => void;
}

export default function ProfileSection({ user, onUpdateProfile }: ProfileSectionProps) {
  const [isEditing, setIsEditing] = useState(false);
  const [name, setName] = useState(user?.name || '');

  useEffect(() => {
    if (user) {
      setName(user.name);
    }
  }, [user]);

  if (!user) return null;

  const handleSave = () => {
    onUpdateProfile(name);
    setIsEditing(false);
  };

  return (
    <section style={{ marginBottom: '40px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid #eee', paddingBottom: '10px' }}>
        <h2 style={{ fontSize: '1.2rem', margin: 0, color: 'black' }}>프로필 정보</h2>
        {!isEditing ? (
          <button onClick={() => setIsEditing(true)} style={{ padding: '8px 16px', border: '1px solid #ddd', borderRadius: '8px', background: '#fff', cursor: 'pointer' }}>
            수정
          </button>
        ) : (
          <div>
            <button onClick={() => setIsEditing(false)} style={{ padding: '8px 16px', border: 'none', background: 'none', cursor: 'pointer', marginRight: '8px' }}>
              취소
            </button>
            <button onClick={handleSave} style={{ padding: '8px 16px', border: 'none', borderRadius: '8px', background: '#007bff', color: 'white', cursor: 'pointer' }}>
              저장
            </button>
          </div>
        )}
      </div>

      <div style={{ display: 'flex', alignItems: 'center', padding: '20px 0' }}>
        <UserAvatar url={user.avatarUrl} />
        <div style={{ marginLeft: '20px' }}>
          {isEditing ? (
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              style={{ fontSize: '1.5rem', fontWeight: 'bold', border: '1px solid #ccc', borderRadius: '4px', padding: '5px' }}
            />
          ) : (
            <p style={{ margin: 0, fontSize: '1.5rem', fontWeight: 'bold', color: 'black' }}>{user.name}</p>
          )}
          <p style={{ margin: '5px 0 0', color: '#555' }}>{user.email}</p>
        </div>
      </div>
    </section>
  );
}