import { NextResponse } from 'next/server';
import { cookies } from 'next/headers';
import { springBootLogin } from '@/lib/api';

export async function POST(request: Request) {
  try {
    const { email, password } = await request.json();

    const data = await springBootLogin({ email, password });

    const { accessToken, refreshToken, ...userData } = data;

    (await cookies()).set('accessToken', accessToken, {
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production',
      path: '/',
      maxAge: 60 * 60 * 24 * 7,
    });

    (await cookies()).set('refreshToken', refreshToken, {
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production',
      path: '/',
      maxAge: 60 * 60 * 24 * 30,
    });

    return NextResponse.json({ success: true, user: userData });

  } catch (error: unknown) {
    console.error('API 라우트 - 로그인 처리 중 오류 발생:', error);
    const errorMessage = error instanceof Error ? error.message : '로그인 처리 중 오류가 발생했습니다.';
    return NextResponse.json(
      { success: false, message: errorMessage },
      { status: 401 }
    );
  }
}