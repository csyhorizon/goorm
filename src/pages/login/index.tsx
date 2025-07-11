import React, { useState } from 'react';
import { useLoginMutation } from '@/lib/api';
import { useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
// ✅ Input 대신 HTML input 사용으로 테스트 가능
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';
import { Eye, EyeOff } from 'lucide-react';

interface LoginForm {
  username: string;
  password: string;
}

const LoginPage: React.FC = () => {
  const [login, { isLoading }] = useLoginMutation();
  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors } } = useForm<LoginForm>();
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => setShowPassword(prev => !prev);

  const onSubmit = async (data: LoginForm) => {
    try {
      const result = await login({
        username: data.username,
        password: data.password
      }).unwrap();

      localStorage.setItem('token', result.token);
      toast.success('로그인 성공!');
      navigate('/');
    } catch {
      toast.error('로그인에 실패했습니다.');
    }
  };

  return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4">
        <div className="w-full max-w-sm bg-white border border-gray-300 p-8">
          {/* 🆕 타이틀 추가 */}
          <div className="text-center mb-8">
            <h1 className="text-4xl font-light mb-4 text-black" style={{ fontFamily: 'cursive' }}>
              Uniqram
            </h1>
            <p className="text-gray-600 text-sm font-medium">
              계정에 로그인하세요
            </p>
          </div>

          <form onSubmit={handleSubmit(onSubmit)} className="space-y-2">
            <input
                {...register('username', { required: '사용자 이름을 입력해주세요' })}
                type="text"
                placeholder="사용자 이름"
                className="w-full px-2 py-2 text-xs text-black bg-gray-50 border border-gray-300 rounded-sm focus:border-gray-400 focus:bg-white"
            />
            {errors.username && (
                <p className="text-red-500 text-xs mt-1">{errors.username.message}</p>
            )}

            <div className="relative">
              <input
                  {...register('password', { required: '비밀번호를 입력해주세요' })}
                  type={showPassword ? 'text' : 'password'}
                  placeholder="비밀번호"
                  className="w-full pr-10 px-2 py-2 text-xs text-black bg-gray-50 border border-gray-300 rounded-sm focus:border-gray-400 focus:bg-white"
              />
              <button
                  type="button"
                  onClick={togglePasswordVisibility}
                  className="absolute top-1/2 right-3 -translate-y-1/2 text-gray-500"
              >
                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
              </button>
              {errors.password && (
                  <p className="text-red-500 text-xs mt-1">{errors.password.message}</p>
              )}
            </div>

            <div className="pt-4">
              <Button
                  type="submit"
                  className="w-full bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 text-sm rounded-sm"
                  disabled={isLoading}
              >
                {isLoading ? '로그인 중...' : '로그인'}
              </Button>
              <p className="text-center text-sm mt-4 text-black">
                계정이 없으신가요?{' '}
                <Link to="/signup" className="text-blue-900 font-medium">
                  회원가입
                </Link>
              </p>
            </div>
          </form>
        </div>
      </div>
  );
};

export default LoginPage;
