import { NextResponse } from 'next/server';
import { cookies } from 'next/headers';

export async function POST(request: Request) {
  try {
    (await cookies()).delete('accessToken');
    (await cookies()).delete('refreshToken');

    return NextResponse.json({ success: true, message: '로그아웃 되었습니다.' });

  } catch (error: any) {
    console.error('API 라우트 - 로그아웃 처리 중 오류 발생:', error);
    return NextResponse.json(
      { success: false, message: error.message || '로그아웃 처리 중 오류가 발생했습니다.' },
      { status: 500 }
    );
  }
}