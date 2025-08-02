'use client';

interface User {
  name: string;
  email: string;
}

interface ProfileSectionProps {
  user: User | null;
}

export default function ProfileSection({ user }: ProfileSectionProps) {
  if (!user) {
    return null;
  }

  return (
    <section style={{ marginBottom: '40px' }}>
      <div style={{ borderBottom: '1px solid #eee', paddingBottom: '10px' }}>
        <h2 style={{ fontSize: '1.2rem', margin: 0, color: 'black' }}>
          프로필 정보
        </h2>
      </div>

      <div style={{ padding: '20px 0' }}>
        <p style={{ margin: 0, fontSize: '1.5rem', fontWeight: 'bold', color: 'black' }}>
          {user.name}
        </p>
        <p style={{ margin: '5px 0 0', color: '#555' }}>
          {user.email}
        </p>
      </div>
    </section>
  );
}