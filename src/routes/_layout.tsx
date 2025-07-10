// ========================================
// 🏗️ _layout.tsx - 모든 페이지의 공통 레이아웃
// ========================================
// 이 파일은 모든 페이지에서 공통으로 사용되는 레이아웃을 정의합니다.
// 사이드바, 헤더, 푸터 등이 여기에 들어갈 수 있습니다.

// 📦 필요한 컴포넌트들을 가져오기
import { Outlet } from 'react-router-dom'; // 하위 페이지를 표시할 자리
import { Toaster } from "@/components/ui/toaster"; // 토스트 알림 컴포넌트
import { Toaster as Sonner } from "@/components/ui/sonner"; // 다른 스타일의 토스트 알림
import { TooltipProvider } from "@/components/ui/tooltip"; // 툴팁 기능 제공
import AuthWrapper from "@/components/AuthWrapper"; // 인증 상태 관리 래퍼

import { Sidebar } from "@/components/Sidebar";

// 🎯 Layout 컴포넌트 - 모든 페이지의 기본 틀
export default function Layout() {
    return (
        // TooltipProvider: 마우스 오버 시 툴팁을 보여주는 기능 제공
        <TooltipProvider>
            {/* AuthWrapper: 사용자 로그인 상태를 확인하고 관리 */}
            <AuthWrapper>
                {/* 전체 레이아웃: 사이드바 + 페이지 콘텐츠 */}
                <div className="flex min-h-screen">
                    {/* 사이드바 */}
                    <Sidebar />

                    {/* 페이지 콘텐츠 */}
                    <main className="flex-1">
                        {/* Outlet: 실제 페이지 내용이 여기에 렌더링됨 */}
                        {/* 예: /login 경로면 LoginPage가, /profile 경로면 ProfilePage가 여기에 표시 */}
                        <Outlet />
                    </main>
                </div>
            </AuthWrapper>

            {/* Toaster: 화면 우상단에 나타나는 알림 메시지 */}
            {/* 예: "로그인 성공!", "게시물이 저장되었습니다" 등 */}
            <Toaster />

            {/* Sonner: 다른 스타일의 알림 메시지 */}
            {/* 더 모던한 디자인의 토스트 알림 */}
            <Sonner />
        </TooltipProvider>
    );
}
