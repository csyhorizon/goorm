'use client';

import { useState, useRef, MouseEvent } from 'react';

interface CategoryFilterProps {
  onSelectCategory: (category: string) => void;
  selectedCategory: string;
}

const CATEGORIES = ['전체', '카페', '음식점', '공원', '쇼핑', '관광지', '병원', '기타', ];

export default function CategoryFilter({ onSelectCategory, selectedCategory }: CategoryFilterProps) {
  const scrollRef = useRef<HTMLDivElement>(null);
  const [isDragging, setIsDragging] = useState(false);
  const [startX, setStartX] = useState(0);
  const [scrollLeft, setScrollLeft] = useState(0);

  const onMouseDown = (e: MouseEvent<HTMLDivElement>) => {
    e.preventDefault();
    if (!scrollRef.current) return;
    setIsDragging(true);
    setStartX(e.pageX - scrollRef.current.offsetLeft);
    setScrollLeft(scrollRef.current.scrollLeft);
  };

  const onMouseLeaveOrUp = () => {
    setIsDragging(false);
  };

  const onMouseMove = (e: MouseEvent<HTMLDivElement>) => {
    if (!isDragging || !scrollRef.current) return;
    const x = e.pageX - scrollRef.current.offsetLeft;
    const walk = (x - startX) * 1;
    scrollRef.current.scrollLeft = scrollLeft - walk;
  };


  return (
    <div
      ref={scrollRef}
      onMouseDown={onMouseDown}
      onMouseLeave={onMouseLeaveOrUp}
      onMouseUp={onMouseLeaveOrUp}
      onMouseMove={onMouseMove}
      style={{
        position: 'absolute',
        top: '75px',
        left: '50%',
        transform: 'translateX(-50%)',
        zIndex: 10,
        width: '30%',
        minWidth: '300px',
        overflowX: 'auto',
        whiteSpace: 'nowrap',
        paddingBottom: '10px',
        cursor: 'grab',
      }}
      className="category-scroll"
    >
      {CATEGORIES.map((category) => (
        <button
          key={category}
          onClick={() => onSelectCategory(category)}
          style={{
            display: 'inline-block',
            padding: '6px 12px',
            marginRight: '8px',
            borderRadius: '20px',
            border: '1px solid #ddd',
            cursor: 'pointer',
            backgroundColor: selectedCategory === category ? '#ffc107' : 'white',
            color: 'black',
            fontWeight: selectedCategory === category ? 'bold' : 'normal',
            fontSize: '0.8rem',
          }}
        >
          {category}
        </button>
      ))}
    </div>
  );
}