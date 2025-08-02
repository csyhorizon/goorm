'use client';

import { createContext, useState, useContext, ReactNode, useEffect } from 'react';

interface User {
  name: string;
  email: string;
}

interface AuthContextType {
  user: User | null;
  accessToken: string | null;
  isLoggedIn: boolean;
  loading: boolean;
  login: (userData: User, token: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const storedUser = localStorage.getItem('user');
      const storedToken = localStorage.getItem('accessToken');
      if (storedUser && storedToken) {
        setUser(JSON.parse(storedUser));
        setAccessToken(storedToken);
      }
      setLoading(false);
    }
  }, []);

  const login = (userData: User, token: string) => {
    setUser(userData);
    setAccessToken(token);
  };

  const logout = () => {
    setUser(null);
    setAccessToken(null);
  };

  const value = {
    user,
    accessToken,
    isLoggedIn: !!user,
    loading,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}