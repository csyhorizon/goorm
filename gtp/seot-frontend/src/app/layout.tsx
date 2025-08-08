import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import Script from "next/script";
import BottomNav from "@/components/layout/BottomNav";
import { ToastProvider } from "@/contexts/ToastContext";
import { AuthProvider } from "@/contexts/AuthContext";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "SEOT",
  description: "SEOT - SEO Tools",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
      >
        <AuthProvider>
          <ToastProvider>
            <header style={{
              position: 'fixed',  
              top: 0,
              left: 0,
              right: 0,
              display: 'flex',
              justifyContent: 'flex-end',
              padding: '10px 20px',
              zIndex: 1000,
              pointerEvents: 'none',
            }}>
              {/* 알림 벨을 여기서 제거합니다 */}
            </header>

            <main style={{ paddingBottom: '60px' }}>
              {children}
            </main>
            <BottomNav />
          </ToastProvider>
        </AuthProvider>

        <Script
          src={`//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.NEXT_PUBLIC_KAKAO_MAP_APP_KEY}&autoload=false&libraries=services,clusterer,drawing`}
          strategy="beforeInteractive"
        />
        <Script
          src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"
          strategy="beforeInteractive"
        />
      </body>
    </html>
  );
}