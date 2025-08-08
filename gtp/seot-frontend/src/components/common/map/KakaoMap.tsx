'use client';

import { useState, useEffect } from 'react';
import CategoryFilter from './CategoryFilter';
import CustomOverlays from './CustomOverlays';
import SearchControl from './SearchControl';
import PlaceDetailOverlay from './PlaceDetailOverlay';
import { getAllStores, Store } from '@/lib/apis/store.api';

declare global {
  interface Window {
    kakao: any;
  }
}

export default function KakaoMap() {
  const [map, setMap] = useState<any>(null);
  const [dbPlaces, setDbPlaces] = useState<Store[]>([]);
  const [selectedCategory, setSelectedCategory] = useState('전체');
  const [selectedPlace, setSelectedPlace] = useState<Store | null>(null);

  useEffect(() => {
    window.kakao.maps.load(() => {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(37.5665, 126.978),
        level: 8,
      };
      const newMap = new window.kakao.maps.Map(container, options);
      
      window.kakao.maps.event.addListener(newMap, 'click', () => {
        setSelectedPlace(null);
      });
      
      setMap(newMap);
    });
  }, []);
  
  useEffect(() => {
    const fetchStores = async () => {
      try {
        const stores = await getAllStores();
        setDbPlaces(stores);
      } catch (error) {
        console.error("가게 정보를 불러오는 데 실패했습니다:", error);
      }
    };
    
    fetchStores();
  }, []);

  useEffect(() => {
    if (!map) return;

    const showPlaceOnIdle = () => {
      const center = map.getCenter();
      let closestPlace: Store | null = null;
      let minDistance = Infinity;

      const placesToShow = selectedCategory === '전체' 
        ? dbPlaces 
        : dbPlaces.filter(p => p.category === selectedCategory);

      placesToShow.forEach(place => {
        const placePosition = new window.kakao.maps.LatLng(place.lat, place.lng);
        const distance = Math.sqrt(
          Math.pow(center.getLat() - placePosition.getLat(), 2) +
          Math.pow(center.getLng() - placePosition.getLng(), 2)
        );
        if (distance < minDistance) {
          minDistance = distance;
          closestPlace = place;
        }
      });
      
      const threshold = 0.00005 * Math.pow(2, 7 - map.getLevel());

      if (closestPlace && minDistance < threshold) {
        setSelectedPlace(closestPlace);
      } else {
        setSelectedPlace(null);
      }
    };

    window.kakao.maps.event.addListener(map, 'idle', showPlaceOnIdle);

    return () => {
      window.kakao.maps.event.removeListener(map, 'idle', showPlaceOnIdle);
    };
  }, [map, dbPlaces, selectedCategory]);

  const handleSelectPlace = (place: Store) => {
    setSelectedPlace(place);
    map.panTo(new window.kakao.maps.LatLng(place.lat, place.lng));
  };

  return (
    <div style={{ position: 'relative', width: '100vw', height: 'calc(100vh - 60px)' }}>
      <div id="map" style={{ width: '100%', height: '100%' }} />
      {map && (
        <>
          <SearchControl map={map} />
          <CategoryFilter 
            selectedCategory={selectedCategory} 
            onSelectCategory={(category) => {
              setSelectedCategory(category);
              setSelectedPlace(null);
            }} 
          />
          <CustomOverlays 
            map={map} 
            places={dbPlaces} 
            selectedCategory={selectedCategory}
            onSelect={handleSelectPlace}
          />
          {selectedPlace && (
            <PlaceDetailOverlay 
              place={selectedPlace} 
              map={map} 
              onClose={() => setSelectedPlace(null)}
            />
          )}
        </>
      )}
    </div>
  );
}