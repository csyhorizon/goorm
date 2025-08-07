import { NextResponse } from 'next/server';
import { cookies } from 'next/headers';
import { login } from '@/lib/apis/auth.api';
import { isAxiosError } from 'axios';

export async function POST(request: Request) {
  try {
    const { email, password } = await request.json();

    const data = await login({ email, password });

    (await cookies()).set('refreshToken', data.refreshToken, {
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production',
      path: '/',
      maxAge: 60 * 60 * 24 * 14,
    });

    (await cookies()).set('accessToken', data.accessToken, {
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production',
      path: '/',
      maxAge: 60 * 60,
    });

    return NextResponse.json({
      success: true,
      user: data.user,
      accessToken: data.accessToken,
    });

  } catch (error: unknown) {
    console.error('API 라우트 - 로그인 처리 중 오류 발생:', error);

    if (isAxiosError(error)) {
      const backendMessage = error.response?.data?.message || '서버에서 오류가 발생했습니다.';
      const backendStatus = error.response?.status || 500;

      return NextResponse.json(
        { success: false, message: backendMessage },
        { status: backendStatus }
      );
    }
    
    const errorMessage = '로그인 처리 중 알 수 없는 오류가 발생했습니다.';
    return NextResponse.json(
      { success: false, message: errorMessage },
      { status: 500 }
    );
  }
}