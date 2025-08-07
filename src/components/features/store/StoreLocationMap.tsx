'use client';

import { useEffect } from 'react';

// window 객체에 kakao 속성을 추가하기 위한 타입 확장
declare global {
  interface Window {
    kakao: any;
  }
}

interface StoreLocationMapProps {
  lat: number;
  lng: number;
}

export default function StoreLocationMap({ lat, lng }: StoreLocationMapProps) {
  useEffect(() => {
    // 위도/경도 값이 없거나 카카오맵 스크립트가 로드되지 않았으면 실행하지 않음
    if (!lat || !lng || !window.kakao || !window.kakao.maps) return;

    window.kakao.maps.load(() => {
      const container = document.getElementById('store-map');
      if (!container) return; // div가 렌더링되지 않았으면 실행 중단

      const options = {
        center: new window.kakao.maps.LatLng(lat, lng),
        level: 3,
      };
      const map = new window.kakao.maps.Map(container, options);
      const marker = new window.kakao.maps.Marker({
        position: new window.kakao.maps.LatLng(lat, lng)
      });
      marker.setMap(map);
    });
  }, [lat, lng]);

  if (!lat || !lng) {
    return (
      <section style={{ padding: '16px' }}>
        <h2 style={{ fontSize: '1.2rem', marginBottom: '16px' }}>위치 정보</h2>
        <p>위치 정보가 등록되지 않았습니다.</p>
      </section>
    );
  }

  return (
    <section style={{ padding: '16px' }}>
      <h2 style={{ fontSize: '1.2rem', marginBottom: '16px' }}>위치 정보</h2>
      <div id="store-map" style={{ width: '100%', height: '250px', borderRadius: '8px' }}></div>
    </section>
  );
}
