
import React from 'react';
import { useLoginMutation } from '@/lib/api';
import { useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';
import bcrypt from 'bcryptjs'; // bcrypt 라이브러리 임포트

interface LoginForm {
  username: string;
  password: string;
}

const LoginPage: React.FC = () => {
  const [login, { isLoading }] = useLoginMutation();
  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors } } = useForm<LoginForm>();

  const onSubmit = async (data: LoginForm) => {
    try {
      // 비밀번호를 bcrypt로 해시화 (rounds: 10)
      const hashedPassword = await bcrypt.hash(data.password, 10);
      console.log('🔐 비밀번호 해시화 완료');

      const result = await login({ username: data.username, password: hashedPassword }).unwrap();
      localStorage.setItem('token', result.token);
      toast.success('로그인 성공!');
      navigate('/');
    } catch {
      toast.error('로그인에 실패했습니다.');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <Card className="w-full max-w-md">
        <CardHeader>
          <CardTitle className="text-2xl text-center">로그인</CardTitle>
          <CardDescription className="text-center">
            계정에 로그인하세요
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div>
              <Input
                {...register('username', { 
                  required: '사용자 이름을 입력해주세요'
                })}
                type="text"
                placeholder="사용자 이름"
                className="w-full"
              />
              {errors.username && (
                <p className="text-red-500 text-sm mt-1">{errors.username.message}</p>
              )}
            </div>

            <div>
              <Input
                {...register('password', { 
                  required: '비밀번호를 입력해주세요'
                })}
                type="text"
                placeholder="비밀번호 (아무 값이나 가능)"
                className="w-full"
              />
              {errors.password && (
                <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>
              )}
            </div>

            <Button 
              type="submit" 
              className="w-full" 
              disabled={isLoading}
            >
              {isLoading ? '로그인 중...' : '로그인'}
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
};

export default LoginPage;
