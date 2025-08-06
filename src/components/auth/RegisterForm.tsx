'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { createStore, CreateStoreRequest, StoreCategory, STORE_CATEGORY_OPTIONS, CreateStoreLocationRequest } from '@/lib/apis/store.api';
import { register, login } from '@/lib/apis/auth.api';
import { isAxiosError } from 'axios';
import { createStoreLocation } from '@/lib/apis/store.api';

declare global {
  interface Window {
    kakao: any;
    daum: any;
  }
}

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

  const [storeName, setStoreName] = useState('');
  const [storeAddress, setStoreAddress] = useState('');
  const [storePhone, setStorePhone] = useState('');
  const [storeDescription, setStoreDescription] = useState('');
  const [startTime, setStartTime] = useState('09:00');
  const [endTime, setEndTime] = useState('18:00');
  const [storeCategory, setStoreCategory] = useState<StoreCategory>(
    (STORE_CATEGORY_OPTIONS[0]?.value || 'OTHER') as StoreCategory
  );

  const [latitude, setLatitude] = useState<number | null>(null);
  const [longitude, setLongitude] = useState<number | null>(null);
  const [mapLoaded, setMapLoaded] = useState(false);
  const [geocoder, setGeocoder] = useState<any>(null);

  useEffect(() => {
    if (window.kakao && window.kakao.maps) {
      window.kakao.maps.load(() => {
        setMapLoaded(true);
        setGeocoder(new window.kakao.maps.services.Geocoder());
      });
    }
  }, []);

  const handleAddressSearch = () => {
    if (!mapLoaded) {
      setError("맵 API가 아직 로드되지 않았습니다.");
      return;
    }

    new window.daum.Postcode({
      oncomplete: function (data: { userSelectedType: string; roadAddress: any; jibunAddress: any; }) {
        const selectedAddress = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
        setStoreAddress(selectedAddress);

        // 주소-좌표 변환
        if (geocoder) {
          geocoder.addressSearch(selectedAddress, (result: any[], status: any) => {
            if (status === window.kakao.maps.services.Status.OK) {
              const coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
              setLatitude(coords.getLat());
              setLongitude(coords.getLng());
              setError(null);
            } else {
              setLatitude(null);
              setLongitude(null);
              setError("주소에 대한 좌표를 찾을 수 없습니다.");
            }
          });
        }
      }
    }).open();
  };

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

    if (role === 'OWNER') {
      if (!storeName || !storeAddress || !storePhone || !storeDescription) {
        setError('가게 정보를 모두 입력해주세요.');
        setLoading(false);
        return;
      }
      if (latitude === null || longitude === null) {
        setError('가게 주소를 검색하여 좌표를 등록해주세요.');
        setLoading(false);
        return;
      }
    }

    try {
      await register({ username, email, password, confirmPassword, role });
      await login({ email, password });

      if (role === 'OWNER') {
        const storeData: CreateStoreRequest = {
          name: storeName,
          address: storeAddress,
          phone_number: storePhone,
          description: storeDescription,
          category: storeCategory,
          startDate: startTime,
          endDate: endTime
        };
        const newStore = await createStore(storeData);

        const createStoreLocationRequest: CreateStoreLocationRequest = {
          latitude: latitude!,
          longitude: longitude!
        };
        await createStoreLocation(newStore.id, createStoreLocationRequest);
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
        <input type="text" id="name" name="username" value={username} onChange={(e) => setUsername(e.target.value)} required className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
      </div>
      <div>
        <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">이메일</label>
        <input type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
      </div>
      <div>
        <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">비밀번호</label>
        <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} required className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
      </div>
      <div>
        <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700 mb-1">비밀번호 확인</label>
        <input type="password" id="confirmPassword" name="confirmPassword" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-2">회원 유형</label>
        <div className="flex items-center space-x-6">
          <div className="flex items-center">
            <input id="role-user" name="role" type="radio" value="USER" checked={role === 'USER'} onChange={(e) => setRole(e.target.value)} className="h-4 w-4 text-indigo-600 border-gray-300 focus:ring-indigo-500" />
            <label htmlFor="role-user" className="ml-2 block text-sm text-gray-900">일반 사용자</label>
          </div>
          <div className="flex items-center">
            <input id="role-owner" name="role" type="radio" value="OWNER" checked={role === 'OWNER'} onChange={(e) => setRole(e.target.value)} className="h-4 w-4 text-indigo-600 border-gray-300 focus:ring-indigo-500" />
            <label htmlFor="role-owner" className="ml-2 block text-sm text-gray-900">사장님</label>
          </div>
        </div>
      </div>

      {role === 'OWNER' && (
        <div className="border-t border-gray-200 mt-4 pt-4 space-y-4">
          <h3 className="text-lg font-medium leading-6 text-gray-900">가게 정보 등록</h3>
          <div>
            <label htmlFor="storeName" className="block text-sm font-medium text-gray-700 mb-1">가게 이름</label>
            <input type="text" id="storeName" value={storeName} onChange={(e) => setStoreName(e.target.value)} required={role === 'OWNER'} className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
          </div>
          <div className="relative">
            <label htmlFor="storeAddress" className="block text-sm font-medium text-gray-700 mb-1">가게 주소</label>
            <div className="flex">
              <input type="text" id="storeAddress" value={storeAddress} readOnly required={role === 'OWNER'} className="mt-1 flex-1 block w-full px-3 py-2 border border-gray-300 rounded-l-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm bg-gray-50 cursor-not-allowed" />
              <button
                type="button"
                onClick={handleAddressSearch}
                disabled={!mapLoaded}
                style={{ backgroundColor: 'gray', color: 'white' }}
                className="mt-1 px-4 py-2 text-sm font-medium rounded-r-md hover:bg-amber-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500"
              >
                주소 검색
              </button>
            </div>
            {latitude && longitude && (
              <p className="mt-2 text-sm text-green-600">주소를 추가했습니다!</p>
            )}
          </div>
          <div>
            <label htmlFor="storePhone" className="block text-sm font-medium text-gray-700 mb-1">전화번호</label>
            <input type="text" id="storePhone" value={storePhone} onChange={(e) => setStorePhone(e.target.value)} required={role === 'OWNER'} className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
          </div>
          <div>
            <label htmlFor="storeCategory" className="block text-sm font-medium text-gray-700 mb-1">가게 카테고리</label>
            <select id="storeCategory" value={storeCategory} onChange={(e) => setStoreCategory(e.target.value as StoreCategory)} required={role === 'OWNER'} className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
              {STORE_CATEGORY_OPTIONS.map(option => (
                <option key={option.value} value={option.value}>{option.label}</option>
              ))}
            </select>
          </div>
          <div className="flex gap-4">
            <div className="w-1/2">
              <label htmlFor="startTime" className="block text-sm font-medium text-gray-700 mb-1">영업 시작 시간</label>
              <input type="time" id="startTime" value={startTime} onChange={(e) => setStartTime(e.target.value)} required={role === 'OWNER'} className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
            </div>
            <div className="w-1/2">
              <label htmlFor="endTime" className="block text-sm font-medium text-gray-700 mb-1">영업 종료 시간</label>
              <input type="time" id="endTime" value={endTime} onChange={(e) => setEndTime(e.target.value)} required={role === 'OWNER'} className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
            </div>
          </div>
          <div>
            <label htmlFor="storeDescription" className="block text-sm font-medium text-gray-700 mb-1">가게 설명</label>
            <textarea id="storeDescription" value={storeDescription} onChange={(e) => setStoreDescription(e.target.value)} required={role === 'OWNER'} rows={3} className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
          </div>
        </div>
      )}

      <button type="submit" disabled={loading} className="btn-primary w-full flex justify-center py-2 px-4">
        {loading ? '처리 중...' : '회원가입'}
      </button>
    </form>
  );
}