'use client';

import StoreHeader from "./StoreHeader";
import StoreMenuList from "./StoreMenuList";
import StoreNoticeList from "./StoreNoticeList";
import StoreLocationMap from "./StoreLocationMap";

interface StoreData {
  id: number;
  name: string;
  category: string;
  imageUrl: string;
  lat: number;
  lng: number;
  menuItems: { id: number; name: string; price: number }[];
  notices: { id: number; title: string; date: string }[];
}

export default function StoreDetail({ store }: { store: StoreData }) {
  return (
    <div>
      <StoreHeader name={store.name} category={store.category} imageUrl={store.imageUrl} />
      <StoreMenuList menuItems={store.menuItems} />
      <StoreNoticeList notices={store.notices} />
      <StoreLocationMap lat={store.lat} lng={store.lng} />
    </div>
  );
}