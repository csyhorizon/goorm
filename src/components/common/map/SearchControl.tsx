'use client';

import { useState, useEffect, useRef } from 'react';

interface Place {
  place_name: string;
  road_address_name: string;
  address_name: string;
  x: string;
  y: string;
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export default function SearchControl({ map }: { map: any }) {
  const [keyword, setKeyword] = useState('');
  const [places, setPlaces] = useState<Place[]>([]);
  const [showSuggestions, setShowSuggestions] = useState(true);
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const psRef = useRef<any>(null);

  useEffect(() => {
    if (!psRef.current) {
      psRef.current = new window.kakao.maps.services.Places();
    }
  }, []);

  useEffect(() => {
    if (!psRef.current || !keyword.trim()) {
      setPlaces([]);
      return;
    }
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
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
    setShowSuggestions(false); // 목록 숨기기

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
    if (!showSuggestions) {
      setShowSuggestions(true);
    }
  };

  return (
    <div style={{ position: 'absolute', top: '20px', left: '50%', transform: 'translateX(-50%)', zIndex: 11, width: '30%', minWidth: '300px' }}>
      {/* 검색창 UI */}
      <div style={{ display: 'flex', alignItems: 'center', backgroundColor: 'white', borderRadius: '20px', boxShadow: '0 2px 6px rgba(0,0,0,0.3)', padding: '8px 16px' }}>
        <span style={{ marginRight: '8px', color: '#555' }}>
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
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
              onMouseDown={(e) => e.preventDefault()} // onBlur 방지
              style={{ padding: '12px 16px', borderBottom: '1px solid #eee', cursor: 'pointer' }}
            >
              <h5 style={{ margin: 0, color: 'black', fontWeight: 600 }}>{place.place_name}</h5>
              <p style={{ fontSize: '0.8rem', color: '#666', margin: '4px 0 0' }}>{place.road_address_name || place.address_name}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}