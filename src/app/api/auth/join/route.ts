import { NextResponse } from 'next/server';
import { register } from '@/lib/apis/auth.api';
import { isAxiosError } from 'axios';

export async function POST(request: Request) {
  try {
    const { username, email, password, confirmPassword, role } = await request.json();

    const data = await register({ username, email, password, confirmPassword, role });

    return NextResponse.json({ success: true, message: data.message || '회원가입에 성공했습니다.' });

  } catch (error: unknown) {
    console.error('API 라우트 - 회원가입 처리 중 오류 발생:', error);

    if (isAxiosError(error)) {
      const backendMessage = error.response?.data?.message || '서버에서 오류가 발생했습니다.';
      const backendStatus = error.response?.status || 500;

      return NextResponse.json(
        { success: false, message: backendMessage },
        { status: backendStatus }
      );
    }

    const errorMessage = error instanceof Error ? error.message : '알 수 없는 오류가 발생했습니다.';
    return NextResponse.json(
      { success: false, message: errorMessage },
      { status: 500 }
    );
  }
}