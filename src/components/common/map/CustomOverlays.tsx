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

    const placesToShow =
      selectedCategory === '전체'
        ? places
        : places.filter((p) => p.category === selectedCategory);

    placesToShow.forEach((place) => {
      const position = new window.kakao.maps.LatLng(place.lat, place.lng);

      const marker = new window.kakao.maps.Marker({ position });
      window.kakao.maps.event.addListener(marker, 'click', () => {
        onSelect(place);
      });
      newMarkers.push(marker);

      const content = document.createElement('div');
      content.className = 'custom-overlay';
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