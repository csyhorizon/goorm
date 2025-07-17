'use client';

import { useState, useEffect } from 'react';
import CategoryFilter from './CategoryFilter';
import CustomOverlays from './CustomOverlays';
import SearchControl from './SearchControl';
import PlaceDetailOverlay from './PlaceDetailOverlay';

declare global {
  interface Window {
    kakao: any;
  }
}

interface CustomPlace {
  id: number;
  name: string;
  category: string;
  lat: number;
  lng: number;
  imageUrl?: string;
  description: string;
}

export default function KakaoMap() {
  const [map, setMap] = useState<any>(null);
  const [dbPlaces, setDbPlaces] = useState<CustomPlace[]>([]);
  const [selectedCategory, setSelectedCategory] = useState('전체');
  const [selectedPlace, setSelectedPlace] = useState<CustomPlace | null>(null);

  useEffect(() => {
    window.kakao.maps.load(() => {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(37.5665, 126.978),
        level: 3,
      };
      const newMap = new window.kakao.maps.Map(container, options);
      
      window.kakao.maps.event.addListener(newMap, 'click', () => {
        setSelectedPlace(null);
      });
      
      setMap(newMap);
    });
  }, []);
  
  useEffect(() => {
    const testData: CustomPlace[] = [
      { id: 1, name: '스타벅스 강남점', category: '카페', lat: 37.498086, lng: 127.028001, imageUrl: 'https://images.unsplash.com/photo-1556742044-3c52d6e88c62?q=80&w=2070&auto=format&fit=crop', description: '강남역 근처의 넓고 쾌적한 스타벅스입니다.' },
      { id: 2, name: '다운타우너 안국', category: '음식점', lat: 37.5779, lng: 126.9855, imageUrl: 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=1998&auto=format&fit=crop', description: '아보카도 버거가 유명한 수제버거 맛집입니다.' },
      { id: 3, name: '블루보틀 역삼', category: '카페', lat: 37.5009, lng: 127.0374, imageUrl: 'https://images.unsplash.com/photo-1511920183353-3c9c93da54e2?q=80&w=1974&auto=format&fit=crop', description: '맛있는 커피와 함께 조용한 시간을 즐겨보세요.' },
    ];
    setDbPlaces(testData);
  }, []);

  useEffect(() => {
    if (!map) return;

    const showPlaceOnIdle = () => {
      const center = map.getCenter();
      let closestPlace = null;
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
      
      const threshold = 0.01 * Math.pow(2, 7 - map.getLevel());

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

  const handleSelectPlace = (place: CustomPlace) => {
    setSelectedPlace(place);
    map.panTo(new window.kakao.maps.LatLng(place.lat, place.lng));
  };

  return (
    <div style={{ position: 'relative', width: '100vw', height: '100vh' }}>
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