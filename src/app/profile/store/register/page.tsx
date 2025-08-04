'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function StoreRegistrationPage() {
  const router = useRouter();

  // 3. 폼 입력을 위한 state 정의
  const [name, setName] = useState('');
  const [address, setAddress] = useState('');
  const [phone, setPhone] = useState('');
  const [category, setCategory] = useState('KOREAN'); // 기본값 설정
  const [description, setDescription] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    try {
      // 4. API 명세서의 StoreCreateRequest에 맞게 데이터 구성
      const storeData = {
        name,
        address,
        phone,
        category,
        description,
      };

      // 5. 백엔드 API 호출

      alert('가게가 성공적으로 등록되었습니다!');
      router.push('/profile'); // 등록 후 프로필 페이지로 이동

    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : '알 수 없는 오류가 발생했습니다.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: '600px', margin: '40px auto', padding: '20px' }}>
      <h1 style={{ fontSize: '2rem', marginBottom: '30px', color: 'black' }}>가게 등록</h1>

      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
        {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}

        <div>
          <label htmlFor="name" style={labelStyle}>가게 이름</label>
          <input
            id="name"
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            style={inputStyle}
            required
          />
        </div>
        
        <div>
          <label htmlFor="address" style={labelStyle}>주소</label>
          <input
            id="address"
            type="text"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
            style={inputStyle}
            required
          />
        </div>

        <div>
          <label htmlFor="phone" style={labelStyle}>전화번호</label>
          <input
            id="phone"
            type="tel"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
            style={inputStyle}
          />
        </div>

        <div>
          <label htmlFor="category" style={labelStyle}>카테고리</label>
          <select
            id="category"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            style={inputStyle}
          >
            <option value="KOREAN">한식</option>
            <option value="CHINESE">중식</option>
            <option value="JAPANESE">일식</option>
            <option value="WESTERN">양식</option>
            <option value="CAFE">카페</option>
          </select>
        </div>

        <div>
          <label htmlFor="description" style={labelStyle}>가게 설명</label>
          <textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            style={{ ...inputStyle, height: '120px', resize: 'vertical' }}
          />
        </div>

        <button
          type="submit"
          disabled={isLoading}
          style={buttonStyle}
        >
          {isLoading ? '등록 중...' : '가게 등록하기'}
        </button>
      </form>
    </div>
  );
}

// 간단한 스타일 객체 (실제 프로젝트에서는 CSS-in-JS나 CSS 파일을 사용하세요)
const labelStyle: React.CSSProperties = {
  display: 'block',
  marginBottom: '8px',
  fontWeight: 'bold',
  color: 'black'
};

const inputStyle: React.CSSProperties = {
  width: '100%',
  padding: '10px',
  fontSize: '1rem',
  border: '1px solid #ccc',
  borderRadius: '4px',
  boxSizing: 'border-box'
};

const buttonStyle: React.CSSProperties = {
  padding: '12px 20px',
  fontSize: '1rem',
  fontWeight: 'bold',
  color: 'white',
  background: '#007bff',
  border: 'none',
  borderRadius: '4px',
  cursor: 'pointer',
};