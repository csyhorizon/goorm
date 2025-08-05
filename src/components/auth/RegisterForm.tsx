'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { createStore, CreateStoreRequest, StoreCategory, STORE_CATEGORY_OPTIONS } from '@/lib/apis/store.api';
import { register, login } from '@/lib/apis/auth.api';
import { isAxiosError } from 'axios';

export default function RegisterForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [username, setUsername] = useState('');
  const [role, setRole] = useState('USER');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState<string | null>(null);
  const router = useRouter();

  // 사장님 회원가입 시 필요한 가게 정보 상태
  const [storeName, setStoreName] = useState('');
  const [storeAddress, setStoreAddress] = useState('');
  const [storePhone, setStorePhone] = useState('');
  const [storeDescription, setStoreDescription] = useState('');
  const [startTime, setStartTime] = useState('09:00'); // 영업 시작 시간 상태
  const [endTime, setEndTime] = useState('18:00');   // 영업 종료 시간 상태
  
  // storeCategory 상태 추가 및 초기값 설정
  const [storeCategory, setStoreCategory] = useState<StoreCategory>(
    (STORE_CATEGORY_OPTIONS[0]?.value || 'OTHER') as StoreCategory
  );

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);
    setLoading(true);

    if (password !== confirmPassword) {
      setError('비밀번호가 일치하지 않습니다.');
      setLoading(false);
      return;
    }
    
    // 사장님 역할 선택 시, 가게 정보 필수 필드 유효성 검사
    if (role === 'OWNER' && (!storeName || !storeAddress || !storePhone || !storeDescription)) {
      setError('가게 정보를 모두 입력해주세요.');
      setLoading(false);
      return;
    }

    try {
      // 1. 회원가입 API를 클라이언트에서 직접 호출
      await register({ username, email, password, confirmPassword, role });

      // 2. 회원가입 성공 후, 자동으로 로그인 API를 호출
      await login({ email, password });

      // 3. 역할이 'OWNER'일 경우에만 가게 등록 API를 호출
      if (role === 'OWNER') {
        const storeData: CreateStoreRequest = {
          name: storeName,
          address: storeAddress,
          phone_number: storePhone,
          description: storeDescription,
          category: storeCategory,
          // 사용자가 입력한 시간 문자열을 그대로 전송
          startDate: startTime,
          endDate: endTime
        };
        await createStore(storeData);
      }

      setSuccess('회원가입 및 가게 등록에 성공했습니다! 로그인 페이지로 이동합니다.');
      setTimeout(() => {
        router.push('/auth/login');
      }, 100);

    } catch (err: unknown) {
      const errorMessage = isAxiosError(err) 
        ? err.response?.data?.message || err.message 
        : err instanceof Error ? err.message : '알 수 없는 오류 발생';
      
      setError(errorMessage);
      console.error('클라이언트 측 회원가입/로그인/가게 등록 에러:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {error && <p className="text-red-500 text-sm mb-4">{error}</p>}
      {success && <p className="text-green-600 text-sm mb-4">{success}</p>}
      
      <div>
        <label htmlFor="name" className="block text-sm font-medium text-gray-700 mb-1">닉네임</label>
        <input
          type="text" id="name" name="username" value={username}
          onChange={(e) => setUsername(e.target.value)} required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>
      <div>
        <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">이메일</label>
        <input
          type="email" id="email" name="email" value={email}
          onChange={(e) => setEmail(e.target.value)} required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>
      <div>
        <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">비밀번호</label>
        <input
          type="password" id="password" name="password" value={password}
          onChange={(e) => setPassword(e.target.value)} required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>
      <div>
        <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700 mb-1">비밀번호 확인</label>
        <input
          type="password" id="confirmPassword" name="confirmPassword" value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)} required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>

      {/* 회원 유형 선택 라디오 버튼 */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">회원 유형</label>
        <div className="flex items-center space-x-6">
          <div className="flex items-center">
            <input
              id="role-user" name="role" type="radio" value="USER"
              checked={role === 'USER'} onChange={(e) => setRole(e.target.value)}
              className="h-4 w-4 text-indigo-600 border-gray-300 focus:ring-indigo-500"
            />
            <label htmlFor="role-user" className="ml-2 block text-sm text-gray-900">일반 사용자</label>
          </div>
          <div className="flex items-center">
            <input
              id="role-owner" name="role" type="radio" value="OWNER"
              checked={role === 'OWNER'} onChange={(e) => setRole(e.target.value)}
              className="h-4 w-4 text-indigo-600 border-gray-300 focus:ring-indigo-500"
            />
            <label htmlFor="role-owner" className="ml-2 block text-sm text-gray-900">사장님</label>
          </div>
        </div>
      </div>

      {/* 사장님 선택 시에만 보이는 가게 정보 입력 필드 */}
      {role === 'OWNER' && (
        <div className="border-t border-gray-200 mt-4 pt-4 space-y-4">
          <h3 className="text-lg font-medium leading-6 text-gray-900">가게 정보 등록</h3>
          <div>
            <label htmlFor="storeName" className="block text-sm font-medium text-gray-700 mb-1">가게 이름</label>
            <input
              type="text" id="storeName" value={storeName} onChange={(e) => setStoreName(e.target.value)}
              required={role === 'OWNER'}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
          <div>
            <label htmlFor="storeAddress" className="block text-sm font-medium text-gray-700 mb-1">가게 주소</label>
            <input
              type="text" id="storeAddress" value={storeAddress} onChange={(e) => setStoreAddress(e.target.value)}
              required={role === 'OWNER'}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
          <div>
            <label htmlFor="storePhone" className="block text-sm font-medium text-gray-700 mb-1">전화번호</label>
            <input
              type="text" id="storePhone" value={storePhone} onChange={(e) => setStorePhone(e.target.value)}
              required={role === 'OWNER'}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
          <div>
            <label htmlFor="storeCategory" className="block text-sm font-medium text-gray-700 mb-1">가게 카테고리</label>
            <select
              id="storeCategory" value={storeCategory} onChange={(e) => setStoreCategory(e.target.value as StoreCategory)}
              required={role === 'OWNER'}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            >
              {STORE_CATEGORY_OPTIONS.map(option => (
                <option key={option.value} value={option.value}>{option.label}</option>
              ))}
            </select>
          </div>
          {/* 시간 입력 필드 추가 */}
          <div className="flex gap-4">
            <div className="w-1/2">
              <label htmlFor="startTime" className="block text-sm font-medium text-gray-700 mb-1">
                영업 시작 시간
              </label>
              <input
                type="time" id="startTime" value={startTime} onChange={(e) => setStartTime(e.target.value)}
                required={role === 'OWNER'}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div className="w-1/2">
              <label htmlFor="endTime" className="block text-sm font-medium text-gray-700 mb-1">
                영업 종료 시간
              </label>
              <input
                type="time" id="endTime" value={endTime} onChange={(e) => setEndTime(e.target.value)}
                required={role === 'OWNER'}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
          </div>
           <div>
            <label htmlFor="storeDescription" className="block text-sm font-medium text-gray-700 mb-1">가게 설명</label>
            <textarea
              id="storeDescription" value={storeDescription} onChange={(e) => setStoreDescription(e.target.value)}
              required={role === 'OWNER'}
              rows={3}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
        </div>
      )}

      {/* 제출 버튼 */}
      <button
        type="submit"
        disabled={loading}
        className="btn-primary w-full flex justify-center py-2 px-4"
      >
        {loading ? '처리 중...' : '회원가입'}
      </button>
    </form>
  );
}
