import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

export async function middleware(request: NextRequest) {
  // 기존 publicPaths 및 인증 검사 제거 (불필요)
  // 모든 요청에 대해 다음 단계로 그냥 넘김

  return NextResponse.next();
}

export const config = {
  matcher: [
    '/((?!api|_next/static|_next/image|favicon.ico|static).*)',
  ],
};
