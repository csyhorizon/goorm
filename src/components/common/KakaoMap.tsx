'use client';

import { useState, useEffect, useRef } from 'react';

declare global {
  interface Window {
    kakao: any;
  }
}

interface Place {
  place_name: string;
  road_address_name: string;
  address_name: string;
  phone: string;
  place_url: string;
  x: string;
  y: string;
}

export default function KakaoMap() {
  const [keyword, setKeyword] = useState('');
  const [map, setMap] = useState<any>(null);
  const [places, setPlaces] = useState<Place[]>([]);
  const [showSuggestions, setShowSuggestions] = useState(true);
  const psRef = useRef<any>(null);

  useEffect(() => {
    window.kakao.maps.load(() => {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(37.5665, 126.978),
        level: 3,
      };
      const newMap = new window.kakao.maps.Map(container, options);
      psRef.current = new window.kakao.maps.services.Places();

      window.kakao.maps.event.addListener(newMap, 'click', () => {
        setPlaces([]);
        setShowSuggestions(false);
      });

      setMap(newMap);
    });
  }, []);

  useEffect(() => {
    if (!psRef.current || !keyword.trim()) {
      setPlaces([]);
      return;
    }
    psRef.current.keywordSearch(keyword, (data: Place[], status: any) => {
      if (status === window.kakao.maps.services.Status.OK) {
        setPlaces(data);
      } else {
        setPlaces([]);
      }
    });
  }, [keyword]);

  const showPlaceOnMap = (place: Place) => {
    setKeyword(place.place_name);
    setShowSuggestions(false);

    const position = new window.kakao.maps.LatLng(place.y, place.x);
    map.panTo(position);

    const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
    const imageSize = new window.kakao.maps.Size(24, 35);
    const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);

    new window.kakao.maps.Marker({
      map: map,
      position: position,
      image: markerImage,
    });
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setKeyword(e.target.value);
    setShowSuggestions(true);
  };

  return (
    <div style={{ position: 'relative', width: '100vw', height: '100vh' }}>
      <div
        style={{
          position: 'absolute',
          top: '20px',
          left: '50%',
          transform: 'translateX(-50%)',
          zIndex: 10,
          width: '30%',
          minWidth: '300px',
        }}
      >
        {/* 검색창 */}
        <div style={{ display: 'flex', alignItems: 'center', backgroundColor: 'white', borderRadius: '20px', boxShadow: '0 2px 6px rgba(0,0,0,0.3)', padding: '8px 16px' }}>
          <span style={{ marginRight: '8px', color: '#555' }}>
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
              <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
            </svg>
          </span>
          <input
            type="text"
            value={keyword}
            onChange={handleInputChange}
            placeholder="장소, 주소 검색"
            style={{ border: 'none', outline: 'none', flexGrow: 1, fontSize: '1rem' }}
          />
        </div>

        {showSuggestions && places.length > 0 && (
          <ul style={{ listStyle: 'none', margin: '8px 0 0', padding: '0', backgroundColor: 'white', borderRadius: '8px', boxShadow: '0 2px 6px rgba(0,0,0,0.1)', maxHeight: '350px', overflowY: 'auto' }}>
            {places.map((place, index) => (
              <li
                key={index}
                onClick={() => showPlaceOnMap(place)}
                style={{ padding: '12px 16px', borderBottom: '1px solid #eee', cursor: 'pointer' }}
              >
                <h5 style={{ margin: 0, color: 'black', fontWeight: 600 }}>{place.place_name}</h5>
                <p style={{ fontSize: '0.8rem', color: '#666', margin: '4px 0 0' }}>{place.road_address_name || place.address_name}</p>
              </li>
            ))}
          </ul>
        )}
      </div>
      <div id="map" style={{ width: '100%', height: '100%' }} />
    </div>
  );
}