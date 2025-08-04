'use client';

import { useState } from 'react';

interface Notice {
  id: number;
  title: string;
  content: string;
}

interface StoreManageFormProps {
  initialNotices: Notice[];
}

export default function StoreManageForm({ initialNotices }: StoreManageFormProps) {
  const [notices, setNotices] = useState<Notice[]>(initialNotices);
  const [newTitle, setNewTitle] = useState('');

  const handleAddNotice = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTitle.trim()) return;

    const newNotice: Notice = {
      id: Date.now(),
      title: newTitle,
      content: '새로운 공지 내용입니다.',
    };

    setNotices([newNotice, ...notices]);
    setNewTitle('');
  };

  const handleDeleteNotice = (id: number) => {
    if (!confirm('정말로 이 공지를 삭제하시겠습니까?')) return;
    setNotices(notices.filter(n => n.id !== id));
  };

  return (
    <div>
      {/* 새 공지 등록 폼 */}
      <form onSubmit={handleAddNotice} style={{ background: '#f8f9fa', padding: '16px', borderRadius: '8px', marginBottom: '32px' }}>
        <h3 style={{ marginTop: 0 }}>새 공지/이벤트 등록</h3>
        <input
          type="text"
          value={newTitle}
          onChange={(e) => setNewTitle(e.target.value)}
          placeholder="제목을 입력하세요"
          style={{ width: '100%', padding: '10px', marginBottom: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
        />
        <button type="submit" style={{ width: '100%', padding: '10px', border: 'none', background: '#007bff', color: 'white', borderRadius: '4px', cursor: 'pointer' }}>
          등록하기
        </button>
      </form>

      {/* 기존 공지 목록 */}
      <div>
        <h3>등록된 공지 목록</h3>
        {notices.map(notice => (
          <div key={notice.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '12px 0', borderBottom: '1px solid #eee' }}>
            <span>{notice.title}</span>
            <button onClick={() => handleDeleteNotice(notice.id)} style={{ background: '#dc3545', color: 'white', border: 'none', padding: '5px 10px', borderRadius: '4px', cursor: 'pointer' }}>
              삭제
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}