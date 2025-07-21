'use client';

interface Notice {
  id: number;
  title: string;
  date: string;
}

export default function StoreNoticeList({ notices }: { notices: Notice[] }) {
  return (
    <section style={{ padding: '16px' }}>
      <h2 style={{ fontSize: '1.2rem', marginBottom: '16px' }}>공지/이벤트</h2>
      <ul style={{ listStyle: 'none', margin: 0, padding: 0 }}>
        {notices.map(notice => (
          <li key={notice.id} style={{ padding: '8px 0', borderBottom: '1px solid #f2f3f5' }}>
            <p style={{ margin: 0, fontWeight: '500' }}>{notice.title}</p>
            <p style={{ margin: '4px 0 0', fontSize: '0.8rem', color: '#888' }}>{notice.date}</p>
          </li>
        ))}
      </ul>
    </section>
  );
}