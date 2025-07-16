import { NextResponse } from 'next/server';
import { springBootRegister } from '@/lib/api';

export async function POST(request: Request) {
  try {
    const { email, password, name } = await request.json();

    const data = await springBootRegister({ email, password, name });

    return NextResponse.json({ success: true, message: data.message || '회원가입에 성공했습니다.' });

  } catch (error: any) {
    console.error('API 라우트 - 회원가입 처리 중 오류 발생:', error);
    return NextResponse.json(
      { success: false, message: error.message || '회원가입 처리 중 오류가 발생했습니다.' },
      { status: 500 }
    );
  }
}