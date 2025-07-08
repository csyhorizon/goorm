import React, { useEffect } from 'react';
import { getToken } from '@/lib/auth';
import { useSelector } from 'react-redux';
import { RootState } from '@/app/store';
import { useLocation, useNavigate } from 'react-router-dom';

interface AuthWrapperProps {
  children: React.ReactNode;
}

const AuthWrapper: React.FC<AuthWrapperProps> = ({ children }) => {
  const bypassAuth = import.meta.env.VITE_BYPASS_AUTH === 'true';
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    if (bypassAuth) {
      console.log("Auth bypassed for development/testing.");
      return;
    }

    // 현재 경로가 로그인 페이지인 경우, 리다이렉트하지 않음
    if (location.pathname === '/login') {
      return;
    }

    // Redux 스토어의 인증 상태와 로컬 스토리지의 토큰을 모두 확인
    if (!isAuthenticated && !getToken()) {
      navigate('/login');
    }
  }, [bypassAuth, isAuthenticated, navigate, location.pathname]);

  // 인증되었거나, 인증이 우회되었거나, 로그인 페이지인 경우에만 자식 컴포넌트 렌더링
  if (bypassAuth || isAuthenticated || getToken() || location.pathname === '/login') {
    return <>{children}</>;
  }

  // 리다이렉트 중에는 아무것도 렌더링하지 않음 (또는 로딩 스피너 등)
  return null;
};

export default AuthWrapper;
