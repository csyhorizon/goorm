'use client';

import { ItemResponse } from '@/lib/apis/store.api';

interface StoreMenuListProps {
  menuItems: ItemResponse[];
}

export default function StoreMenuList({ menuItems }: StoreMenuListProps) {
  return (
    <section style={{ padding: '16px' }}>
      <h2 style={{ fontSize: '1.2rem', marginBottom: '16px' }}>메뉴</h2>
      {menuItems.length > 0 ? (
        <ul style={{ listStyle: 'none', margin: 0, padding: 0 }}>
          {menuItems.map(item => (
            <li key={item.itemId} style={{ display: 'flex', justifyContent: 'space-between', padding: '8px 0', borderBottom: '1px solid #f2f3f5' }}>
              <span>{item.name}</span>
              <span>{item.price.toLocaleString()}원</span>
            </li>
          ))}
        </ul>
      ) : (
        <p>등록된 메뉴가 없습니다.</p>
      )}
    </section>
  );
}
