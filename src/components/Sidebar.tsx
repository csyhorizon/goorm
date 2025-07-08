
import { removeToken } from '@/lib/auth';
import React from 'react';
import { useDispatch } from 'react-redux';
import { setLogout } from '@/features/auth/authSlice';
import { Home, Search, Plus, User, Bell, MessageSquare, MoreHorizontal } from 'lucide-react';


const menuItems = [
  { icon: Home, label: '홈', path: '/' },
  { icon: Search, label: '검색', path: '/search' },
  { icon: Plus, label: '탐색 탭', path: '/explore' },
  { icon: Plus, label: '릴스', path: '/reels' },
  { icon: MessageSquare, label: '메시지', path: '/messages' },
  { icon: Bell, label: '알림', path: '/notifications' },
  { icon: Plus, label: '만들기', path: '/create' },
  { icon: User, label: '대시보드', path: '/dashboard' },
  { icon: User, label: '프로필', path: '/profile' },
  { icon: MessageSquare, label: 'Threads', path: '/threads' },
  { icon: MoreHorizontal, label: '더 보기', path: '/more' },
  { icon: MoreHorizontal, label: '로그아웃', path: '/logout' }, // 로그아웃 항목 추가
];

export const Sidebar = () => {
  const dispatch = useDispatch();

  const handleItemClick = (path: string) => {
    if (path === '/logout') {
      removeToken();
      dispatch(setLogout());
      window.location.href = '/login';
    } else {
      window.location.href = path;
    }
  };

  return (
    <div className="w-64 bg-instagram-dark border-r border-instagram-border h-screen sticky top-0 p-4">
      {/* Logo */}
      <div className="mb-8 p-4">
        <h1 className="text-2xl font-bold text-instagram-text">Uniqram</h1>
      </div>
      
      {/* Menu Items */}
      <nav className="space-y-2">
        {menuItems.map((item, index) => (
          <div
            key={index}
            onClick={() => handleItemClick(item.path)}
            className={`flex items-center space-x-3 p-3 rounded-lg cursor-pointer transition-colors duration-200 ${
              window.location.pathname === item.path
                ? 'bg-instagram-gray text-instagram-text' 
                : 'text-instagram-muted hover:bg-instagram-gray hover:text-instagram-text'
            }`}
          >
            <item.icon size={24} />
            <span className="text-base font-medium">{item.label}</span>
          </div>
        ))}
      </nav>
    </div>
  );
};
