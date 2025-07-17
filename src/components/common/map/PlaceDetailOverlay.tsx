'use client';

import { useEffect } from 'react';

interface CustomPlace {
  id: number;
  name: string;
  category: string;
  lat: number;
  lng: number;
  imageUrl?: string;
  description: string;
}

interface PlaceDetailOverlayProps {
  place: CustomPlace;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  map: any;
  onClose: () => void;
}

export default function PlaceDetailOverlay({ place, map, onClose }: PlaceDetailOverlayProps) {
  useEffect(() => {
    const imageUrl = place.imageUrl || 'https://via.placeholder.com/280x150.png?text=No+Image';
    
    const content = `
      <div class="detail-overlay">
        <div class="detail-header">
          <span class="title">${place.name}</span>
          <button class="close-btn" title="닫기">X</button>
        </div>
        <div class="detail-body">
          <img src="${imageUrl}" alt="${place.name}" class="detail-img"/>
          <p class="description">${place.description}</p>
        </div>
      </div>
    `;

    const position = new window.kakao.maps.LatLng(place.lat, place.lng);
    const overlay = new window.kakao.maps.CustomOverlay({
      content: content,
      position: position,
      yAnchor: 1.1,
      zIndex: 100,
    });
    
    overlay.setMap(map);

    const closeBtn = document.querySelector('.close-btn');
    const closeHandler = (e: Event) => {
      e.stopPropagation();
      onClose();
    };
    if (closeBtn) {
      closeBtn.addEventListener('click', closeHandler, { once: true });
    }

    return () => {
      overlay.setMap(null);
    };
  }, [place, map, onClose]);

  return null;
}