'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';

const MapIcon = ({ isActive }: { isActive: boolean }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill="none"
    stroke={isActive ? '#007bff' : 'currentColor'}
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
    <circle cx="12" cy="10" r="3"></circle>
  </svg>
);

const ProfileIcon = ({ isActive }: { isActive: boolean }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill="none"
    stroke={isActive ? '#007bff' : 'currentColor'}
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
    <circle cx="12" cy="7" r="4"></circle>
  </svg>
);

const CommunityIcon = ({ isActive }: { isActive: boolean }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill="none"
    stroke={isActive ? '#007bff' : 'currentColor'}
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
  >
    <path d="M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0z"></path>
    <path d="M16.24 7.76a3.5 3.5 0 1 1-4.95-4.95"></path>
    <path d="M12.5 15.5a2.5 2.5 0 1 1-3.54-3.54"></path>
  </svg>
);


export default function BottomNav() {
  const pathname = usePathname();

  const navItems = [
    { href: '/community', label: '커뮤니티', icon: CommunityIcon },
    { href: '/', label: '지도', icon: MapIcon },
    { href: '/profile', label: '프로필', icon: ProfileIcon },
  ];

  return (
    <nav style={{
      position: 'fixed',
      bottom: 0,
      left: 0,
      right: 0,
      height: '60px',
      backgroundColor: 'white',
      borderTop: '1px solid #eaeaea',
      display: 'flex',
      justifyContent: 'space-around',
      alignItems: 'center',
      zIndex: 1000,
    }}>
      {navItems.map((item) => {
        const isActive = pathname === item.href;
        return (
          <Link key={item.href} href={item.href} style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            textDecoration: 'none',
            color: isActive ? '#007bff' : '#888',
            fontSize: '12px',
          }}>
            <item.icon isActive={isActive} />
            <span>{item.label}</span>
          </Link>
        );
      })}
    </nav>
  );
}