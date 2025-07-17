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

interface CustomOverlaysProps {
  map: any;
  places: CustomPlace[];
  selectedCategory: string;
  onSelect: (place: CustomPlace) => void;
}

export default function CustomOverlays({ map, places, selectedCategory, onSelect }: CustomOverlaysProps) {
  useEffect(() => {
    if (!map) return;

    const overlays: any[] = [];

    const placesToShow =
      selectedCategory === '전체'
        ? places
        : places.filter((p) => p.category === selectedCategory);

    placesToShow.forEach((place) => {
      const content = document.createElement('div');
      content.className = 'custom-overlay';
      content.innerText = place.name;
      
      content.onclick = () => {
        onSelect(place);
      };

      const position = new window.kakao.maps.LatLng(place.lat, place.lng);

      const customOverlay = new window.kakao.maps.CustomOverlay({
        position: position,
        content: content,
        yAnchor: 1.2,
      });

      customOverlay.setMap(map);
      overlays.push(customOverlay);
    });

    return () => {
      overlays.forEach((overlay) => overlay.setMap(null));
    };
  }, [map, places, selectedCategory, onSelect]);

  return null;
}