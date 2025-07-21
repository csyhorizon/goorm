import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';
import { jwtVerify } from 'jose';

const JWT_SECRET_KEY = new TextEncoder().encode(process.env.JWT_SECRET);

export async function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  const publicPaths = [
    '/auth/login',
    '/auth/register',
    '/api/auth/login',
    '/api/auth/register',
    '/api/auth/logout',
    '/api/auth/refresh',
    '/_next',
    '/favicon.ico',
    '/static',
  ];

  const isPublicPath = publicPaths.some(path => pathname.startsWith(path));

  const accessToken = request.cookies.get('accessToken')?.value;

  let isAuthenticated = false;

  if (accessToken) {
    try {
      await jwtVerify(accessToken, JWT_SECRET_KEY, {
        algorithms: ['HS256'],
      });
      isAuthenticated = true;
    } catch (error: unknown) {
      const errorMessage = error instanceof Error ? error.message : 'JWT 검증 오류';
      console.error('JWT 유효성 검증 실패 또는 만료:', errorMessage);
      isAuthenticated = false;
      const response = NextResponse.redirect(new URL('/auth/login', request.url));
      response.cookies.delete('accessToken');
      response.cookies.delete('refreshToken');
      return response;
    }
  }

  if (isAuthenticated && isPublicPath) {
    return NextResponse.redirect(new URL('/dashboard', request.url));
  }

  // if (!isAuthenticated && !isPublicPath) {
  //   if (pathname.startsWith('/dashboard') || pathname.startsWith('/profile')) {
  //     return NextResponse.redirect(new URL('/auth/login', request.url));
  //   }
  // }

  const response = NextResponse.next();

  return response;
}

export const config = {
  matcher: [
    '/((?!api|_next/static|_next/image|favicon.ico|static).*)',
  ],
};