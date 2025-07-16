import React from 'react';
import Link from 'next/link';

interface AuthLayoutProps {
  children: React.ReactNode;
}

export default function AuthLayout({ children }: AuthLayoutProps) {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 p-4">
      <div className="mb-8 text-center">
        <Link href="/" className="inline-block">
          <h1 className="text-5xl font-extrabold text-yellow-500 tracking-tight">
            SEOT
          </h1>
        </Link>
      </div>

      <div className="w-full max-w-md bg-white rounded-lg shadow-md p-8">
        {children}
      </div>
    </div>
  );
}