import { NextResponse } from 'next/server';
import { register } from '@/lib/apis/auth.api'; 

export async function POST(request: Request) {
  try {
    const { username, email, password, confirmPassword } = await request.json();

    const data = await register({ username, email, password, confirmPassword });

    return NextResponse.json({ success: true, message: data.message || '회원가입에 성공했습니다.' });

  } catch (error: unknown) {
    console.error('API 라우트 - 회원가입 처리 중 오류 발생:', error);
    
    const errorMessage = error instanceof Error ? error.message : '회원가IP 처리 중 오류가 발생했습니다.';
    
    return NextResponse.json(
      { success: false, message: errorMessage },
      { status: 400 }
    );
  }
}