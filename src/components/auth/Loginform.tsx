'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { useAuth } from '@/contexts/AuthContext';

export default function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const router = useRouter();
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });
      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || '로그인 실패');
      }

      const dataUser = data.user;
      const frontUser = {
        name: dataUser.username,
        email: email,
      }

      console.log('로그인 데이터:', frontUser, data.accessToken);

      login(frontUser, data.accessToken);

      console.log('로그인 성공!');
      router.push('/');

    } catch (err: unknown) {
      const errorMessage = err instanceof Error ? err.message : '알 수 없는 오류 발생';
      setError(errorMessage);
      console.error('클라이언트 측 로그인 에러:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {error && <p className="text-red-500 text-sm mb-4">{error}</p>}

      <div>
        <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
          이메일
        </label>
        <input
          type="email"
          id="email"
          name="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>

      <div>
        <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
          비밀번호
        </label>
        <input
          type="password"
          id="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>

      <button
        type="submit"
        disabled={loading}
        className="btn-primary"
      >
        {loading ? '로그인 중...' : '로그인'}
      </button>

      <p className="mt-4 text-center text-sm text-gray-600">
        계정이 없으신가요?{' '}
        <Link href="/auth/register" className="font-medium text-indigo-600 hover:text-indigo-500">
          회원가입하기
        </Link>
      </p>
    </form>
  );
}