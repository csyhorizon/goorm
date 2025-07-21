'use client';

interface AccountSectionProps {
  onLogout: () => void;
  onWithdrawal: () => void;
}

export default function AccountSection({ onLogout, onWithdrawal }: AccountSectionProps) {
  return (
    <section>
      <h2 style={{ fontSize: '1.2rem', borderBottom: '1px solid #eee', paddingBottom: '10px', color: 'black' }}>계정 관리</h2>
      <div style={{ paddingTop: '16px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
        <button onClick={onLogout} style={{ textAlign: 'left', padding: '12px 0', border: 'none', background: 'none', cursor: 'pointer', fontSize: '1rem', color: '#007bff' }}>
          로그아웃
        </button>
        <button onClick={onWithdrawal} style={{ textAlign: 'left', padding: '12px 0', border: 'none', background: 'none', cursor: 'pointer', fontSize: '1rem', color: '#dc3545' }}>
          회원 탈퇴
        </button>
      </div>
    </section>
  );
}