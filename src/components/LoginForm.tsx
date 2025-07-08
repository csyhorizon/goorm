
import { setToken } from '@/lib/auth';
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { setLogin } from '@/features/auth/authSlice';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Label } from './ui/label';
import { Separator } from './ui/separator';
import { Facebook } from 'lucide-react';
import { Api, SigninRequest } from '@/api/api'; // Api 클래스와 SigninRequest 타입 임포트


export const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const dispatch = useDispatch();
  const api = new Api(); // Api 클래스의 인스턴스 생성

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // 생성된 API 클라이언트 코드 사용
      const response = await api.api.signin({ username: email, password: password } as SigninRequest);
      const { token } = response.data; // API 응답에서 토큰을 추출 (응답 구조에 따라 변경될 수 있음)
      setToken(token);
      dispatch(setLogin({ username: email, email: email })); // 예시 사용자 정보
      window.location.href = '/'; // 로그인 성공 후 메인 페이지로 리디렉션
    } catch (error) {
      console.error('Login failed:', error);
      // 로그인 실패 처리 (예: 에러 메시지 표시)
      alert('로그인 실패: 이메일 또는 비밀번호를 확인하세요.');
    }
  };

  const handleFacebookLogin = () => {
    // Facebook 로그인 로직
    console.log('Facebook 로그인');
  };

  return (
    <div className="w-full max-w-sm space-y-6">
      {/* Logo */}
      <div className="text-center mb-8">
        <h1 className="text-4xl font-bold text-instagram-text tracking-tight">
          <span className="bg-gradient-to-r from-instagram-blue to-purple-400 bg-clip-text text-transparent">
            Uniqram
          </span>
        </h1>
      </div>

      {/* Login Form */}
      <form onSubmit={handleLogin} className="space-y-4">
        <div className="space-y-2">
          <Label htmlFor="email" className="text-instagram-muted text-xs">
            전화번호, 사용자 이름 또는 이메일
          </Label>
          <Input
            id="email"
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="bg-instagram-gray border-instagram-border text-instagram-text placeholder:text-instagram-muted focus:border-instagram-blue"
            placeholder="전화번호, 사용자 이름 또는 이메일"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="password" className="text-instagram-muted text-xs">
            비밀번호
          </Label>
          <Input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="bg-instagram-gray border-instagram-border text-instagram-text placeholder:text-instagram-muted focus:border-instagram-blue"
            placeholder="비밀번호"
            required
          />
        </div>

        <Button 
          type="submit"
          className="w-full bg-instagram-blue hover:bg-instagram-blue/90 text-white font-semibold py-2"
        >
          로그인
        </Button>
      </form>

      {/* Divider */}
      <div className="relative">
        <Separator className="bg-instagram-border" />
        <div className="absolute inset-0 flex items-center justify-center">
          <span className="bg-instagram-dark px-4 text-instagram-muted text-sm font-semibold">
            또는
          </span>
        </div>
      </div>

      {/* Facebook Login */}
      <Button
        onClick={handleFacebookLogin}
        variant="outline"
        className="w-full border-instagram-border text-instagram-blue hover:bg-instagram-gray/50 font-semibold"
      >
        <Facebook size={16} className="mr-2" />
        Facebook으로 로그인
      </Button>

      {/* Forgot Password */}
      <div className="text-center">
        <button className="text-instagram-blue text-sm hover:underline">
          비밀번호를 잊으셨나요?
        </button>
      </div>

      {/* Sign Up Link */}
      <div className="text-center border-t border-instagram-border pt-6 mt-8">
        <p className="text-instagram-muted text-sm">
          계정이 없으신가요?{' '}
          <button className="text-instagram-blue font-semibold hover:underline">
            가입하기
          </button>
        </p>
      </div>
    </div>
  );
};
