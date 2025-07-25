import { NextResponse } from 'next/server';
import { cookies } from 'next/headers';
import { login } from '@/lib/apis/auth.api';

export async function POST(request: Request) {
  try {
    const { email, password } = await request.json();

    const data = await login({ email, password });

    (await
      cookies()).set('accessToken', data.accessToken, {
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production',
      path: '/',
      maxAge: 60 * 60,
    });

    (await cookies()).set('refreshToken', data.refreshToken, {
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production',
      path: '/',
      maxAge: 60 * 60 * 24 * 14,
    });

    return NextResponse.json({ success: true, user: data.user });

  } catch (error: unknown) {
    console.error('API 라우트 - 로그인 처리 중 오류 발생:', error);
    const errorMessage = error instanceof Error ? error.message : '로그인 처리 중 오류가 발생했습니다.';
    return NextResponse.json(
      { success: false, message: errorMessage },
      { status: 401 }
    );
  }
}