'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';

interface CustomPlace {
  id: number;
  name: string;
  imageUrl?: string;
  lat: number;
  lng: number;
  description: string;
}

interface PlaceDetailOverlayProps {
  place: CustomPlace;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  map: any;
  onClose: () => void;
}

export default function PlaceDetailOverlay({ place, map, onClose }: PlaceDetailOverlayProps) {
  const router = useRouter();

  useEffect(() => {
    const imageUrl = place.imageUrl || 'https://via.placeholder.com/280x150.png?text=No+Image';
    
    const contentNode = document.createElement('div');
    contentNode.className = 'detail-overlay';
    
    contentNode.innerHTML = `
      <div class="detail-header">
        <span class="title">${place.name}</span>
        <button class="close-btn" title="닫기">X</button>
      </div>
      <div class="detail-body">
        <img src="${imageUrl}" alt="${place.name}" class="detail-img"/>
        <p class="description">${place.description}</p>
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

    // 정보창 위에서 발생하는 마우스/터치 이벤트가 지도에 전달되지 않도록 막습니다.
    const stopPropagation = (e: Event) => e.stopPropagation();
    const eventsToStop = ['mousedown', 'touchstart', 'mouseup', 'touchend', 'click'];
    eventsToStop.forEach(eventType => {
        contentNode.addEventListener(eventType, stopPropagation);
    });

    const closeBtn = contentNode.querySelector('.close-btn');
    const body = contentNode.querySelector('.detail-body');

    const handleClose = () => {
      onClose();
    };
    
    const handleDetailClick = () => {
      router.push(`/stores/${place.id}`);
    };

    if (closeBtn) closeBtn.addEventListener('click', handleClose);
    if (body) body.addEventListener('click', handleDetailClick);

    // 컴포넌트가 사라질 때 이벤트 리스너를 정리합니다.
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