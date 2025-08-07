'use client';

import { useState, useEffect } from 'react';
import { Store } from '@/lib/apis/store.api';

interface CustomOverlaysProps {
  map: any;
  places: Store[];
  selectedCategory: string;
  onSelect: (place: Store) => void;
}

export default function CustomOverlays({ map, places, selectedCategory, onSelect }: CustomOverlaysProps) {
  const [markers, setMarkers] = useState<any[]>([]);
  const [overlays, setOverlays] = useState<any[]>([]);

  useEffect(() => {
    if (!map) return;

    const newMarkers: any[] = [];
    const newOverlays: any[] = [];

    // '전체' 카테고리이거나 선택된 카테고리와 일치하는 장소만 필터링합니다.
    const placesToShow =
      selectedCategory === '전체'
        ? places
        : places.filter((p) => p.category === selectedCategory);

    placesToShow.forEach((place) => {
      // 3. place 객체는 이제 CustomPlace가 아닌 Store 타입입니다.
      //    lat, lng 속성을 사용하므로 기존 로직은 그대로 동작합니다.
      const position = new window.kakao.maps.LatLng(place.lat, place.lng);

      const marker = new window.kakao.maps.Marker({ position });
      window.kakao.maps.event.addListener(marker, 'click', () => {
        onSelect(place);
      });
      newMarkers.push(marker);

      const content = document.createElement('div');
      content.className = 'custom-overlay'; // CSS 스타일링을 위한 클래스
      content.innerText = place.name;
      content.onclick = () => onSelect(place);

      const customOverlay = new window.kakao.maps.CustomOverlay({
        position,
        content,
        yAnchor: 1.2,
      });
      newOverlays.push(customOverlay);
    });

    setMarkers(newMarkers);
    setOverlays(newOverlays);

  }, [map, places, selectedCategory, onSelect]);

  // 마커 클러스터링 및 줌 레벨에 따른 오버레이 표시 로직 (변경 없음)
  useEffect(() => {
    if (!map) return;

    const clusterer = new window.kakao.maps.MarkerClusterer({
      map: map,
      averageCenter: true,
      minLevel: 6,
    });

    const zoomLevel = map.getLevel();
    const nameDisplayThreshold = 4;

    if (zoomLevel <= nameDisplayThreshold) {
      clusterer.clear();
      overlays.forEach(overlay => overlay.setMap(map));
      markers.forEach(marker => marker.setMap(null));
    } else {
      clusterer.addMarkers(markers);
      overlays.forEach(overlay => overlay.setMap(null));
    }

    const handleZoomChanged = () => {
      const currentLevel = map.getLevel();
      if (currentLevel <= nameDisplayThreshold) {
        clusterer.clear();
        overlays.forEach(overlay => overlay.setMap(map));
      } else {
        overlays.forEach(overlay => overlay.setMap(null));
        clusterer.addMarkers(markers);
      }
    };

    window.kakao.maps.event.addListener(map, 'zoom_changed', handleZoomChanged);

    return () => {
      window.kakao.maps.event.removeListener(map, 'zoom_changed', handleZoomChanged);
      clusterer.clear();
      overlays.forEach(overlay => overlay.setMap(null));
    };
  }, [map, markers, overlays]);

  return null;
}