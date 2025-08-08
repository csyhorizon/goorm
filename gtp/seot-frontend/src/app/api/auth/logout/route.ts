import { NextResponse } from 'next/server';
import { cookies } from 'next/headers';
import { logout } from '@/lib/apis/auth.api';
import { createServerApi } from '@/lib/apis/serverClient';

export async function POST() {
  const cookieStore = cookies();
  const serverApi = createServerApi(await cookieStore);

  try {
    await logout(serverApi);
    return NextResponse.json(
      { success: true, message: '로그아웃이 완료되었습니다.' }, { status: 200 }
    );

  } catch (error: unknown) {
    console.error('API 라우트 - 로그아웃 처리 중 오류 발생:', error);
    const errorMessage = error instanceof Error ? error.message : '로그아웃 처리 중 오류가 발생했습니다.';

    return NextResponse.json(
      { success: false, message: errorMessage }, { status: 500 }
    );
  } finally {
    (await cookies()).delete('accessToken');
    (await cookies()).delete('refreshToken');
  }
}