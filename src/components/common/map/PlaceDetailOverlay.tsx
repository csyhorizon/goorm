'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { Store } from '@/lib/apis/store.api';

interface PlaceDetailOverlayProps {
  place: Store; // 타입을 Store로 변경
  map: any;
  onClose: () => void;
}

export default function PlaceDetailOverlay({ place, map, onClose }: PlaceDetailOverlayProps) {
  const router = useRouter();

  useEffect(() => {
    const contentNode = document.createElement('div');
    contentNode.className = 'detail-overlay';
    
    // 2. 이미지(img) 태그를 제거하고 주소(address)를 추가
    contentNode.innerHTML = `
      <div class="detail-header">
        <span class="title">${place.name}</span>
        <button class="close-btn" title="닫기">X</button>
      </div>
      <div class="detail-body">
        <p class="description">${place.description}</p>
        <div class="address-container">
            <span class="address-label">주소</span>
            <span class="address-text">${place.address}</span>
        </div>
      </div>
    `;

    const position = new window.kakao.maps.LatLng(place.lat, place.lng);

    const overlay = new window.kakao.maps.CustomOverlay({
      content: contentNode,
      position: position,
      yAnchor: 1.1,
      zIndex: 100,
    });
    
    overlay.setMap(map);

    const stopPropagation = (e: Event) => e.stopPropagation();
    const eventsToStop = ['mousedown', 'touchstart', 'mouseup', 'touchend', 'click'];
    eventsToStop.forEach(eventType => {
        contentNode.addEventListener(eventType, stopPropagation);
    });

    const closeBtn = contentNode.querySelector('.close-btn');
    const body = contentNode.querySelector('.detail-body');

    const handleClose = () => onClose();
    // 상세 페이지 이동 클릭 이벤트
    const handleDetailClick = () => router.push(`/stores/${place.id}`);

    if (closeBtn) closeBtn.addEventListener('click', handleClose);
    if (body) body.addEventListener('click', handleDetailClick);

    return () => {
      eventsToStop.forEach(eventType => {
          contentNode.removeEventListener(eventType, stopPropagation);
      });
      if (closeBtn) closeBtn.removeEventListener('click', handleClose);
      if (body) body.removeEventListener('click', handleDetailClick);
      overlay.setMap(null);
    };
  }, [place, map, onClose, router]);

  return null;
}