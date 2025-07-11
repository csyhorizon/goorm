"use client";

import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';
import { Eye, EyeOff } from 'lucide-react';
import { useRegisterMutation } from '@/lib/api';

interface SignupForm {
  username: string;
  password: string;
  confirmPassword: string;
}

const SignupPage: React.FC = () => {
  const navigate = useNavigate();
  const [registerUser, { isLoading }] = useRegisterMutation();
  const {
    register: registerField,
    handleSubmit,
    formState: { errors },
    watch
  } = useForm<SignupForm>();

  const password = watch('password');
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(prev => !prev);
  };

  const handleSignup = async (data: SignupForm) => {
    try {
      if (data.password !== data.confirmPassword) {
        toast.error('비밀번호가 일치하지 않습니다.', {
          id: 'password-mismatch',
          duration: 3000,
        });
        return;
      }

      await registerUser({
        username: data.username,
        password: data.password,
        confirmPassword: data.confirmPassword
      }).unwrap();

      toast.success('회원가입이 완료되었습니다! 로그인 해주세요.', {
        id: 'signup-success',
        duration: 3000,
      });

      navigate('/login');

    } catch (error) {
      console.error('회원가입 오류:', error);
      toast.error('회원가입에 실패했습니다. 다시 시도해주세요.', {
        id: 'signup-error',
        duration: 3000,
      });
    }
  };

  return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4">
        <div className="w-full max-w-sm bg-white border border-gray-300 p-8">
          <div className="text-center mb-8">
            <h1 className="text-4xl font-light mb-4 text-black" style={{ fontFamily: "cursive" }}>
              Uniqram
            </h1>
            <p className="text-gray-600 text-sm font-medium">
              친구들의 사진과 동영상을 보려면 가입하세요.
            </p>
          </div>

          <form onSubmit={handleSubmit(handleSignup)} className="space-y-2">
            <Input
                {...registerField('username', {
                  required: '사용자 ID를 입력해주세요',
                })}
                type="text"
                placeholder="사용자 ID"
                className="w-full px-2 py-2 text-xs text-black bg-gray-50 border border-gray-300 rounded-sm focus:border-gray-400 focus:bg-white"
            />
            {errors.username && (
                <p className="text-red-500 text-xs mt-1">{errors.username.message}</p>
            )}

            <div className="relative">
              <Input
                  {...registerField('password', {
                    required: '비밀번호를 입력해주세요',
                    minLength: {
                      value: 6,
                      message: '비밀번호는 최소 6자 이상이어야 합니다',
                    },
                  })}
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

            <Input
                {...registerField('confirmPassword', {
                  required: '비밀번호 확인을 입력해주세요',
                  validate: value => value === password || '비밀번호가 일치하지 않습니다',
                })}
                type="password"
                placeholder="비밀번호 확인"
                className="w-full px-2 py-2 text-xs text-black bg-gray-50 border border-gray-300 rounded-sm focus:border-gray-400 focus:bg-white"
            />
            {errors.confirmPassword && (
                <p className="text-red-500 text-xs mt-1">{errors.confirmPassword.message}</p>
            )}

            <div className="pt-4">
              <Button
                  type="submit"
                  className="w-full bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 text-sm rounded-sm"
                  disabled={isLoading}
              >
                {isLoading ? '가입 중...' : '가입'}
              </Button>
              <p className="text-center text-sm mt-4 text-black">
                계정이 있으신가요?{" "}
                <Link to="/login" className="text-blue-900 font-medium">
                  로그인
                </Link>
              </p>
            </div>
          </form>
        </div>
      </div>
  );
};

export default SignupPage;
