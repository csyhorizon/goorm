// ========================================
// 🚀 App.tsx - React 애플리케이션의 메인 진입점
// ========================================
// 이 파일은 전체 React 앱의 루트 컴포넌트입니다.
// 모든 라우팅, 상태관리, API 클라이언트 설정이 여기서 이루어집니다.

// 📦 필요한 라이브러리들을 가져오기 (import)
// React Query: 서버 상태 관리 (API 호출, 캐싱, 로딩 상태 등)
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
// React Router: 페이지 간 이동을 위한 라우팅 시스템
import { createBrowserRouter, RouterProvider } from "react-router-dom";
// Redux: 전역 상태 관리 (사용자 정보, 앱 설정 등)
import { Provider } from "react-redux";
import { store } from "@/app/store";
import { Toaster } from 'sonner';

// 🗂️ 각 페이지 컴포넌트들을 가져오기
// 파일 기반 라우팅: routes 폴더의 파일들이 자동으로 페이지가 됨
import Layout from "./routes/_layout";                 // 공통 레이아웃 (사이드바, 헤더 등)
import HomePage from "./routes/index";                 // 홈페이지 (/)
import LoginPage from "./routes/login";                // 로그인 페이지 (/login)
import SignupPage from "./routes/signup";              // 회원가입 페이지 (/signup)
import ExplorePage from "./routes/explore";            // 탐색 페이지 (/explore)
import MessagesPage from "./routes/messages";          // 메시지 페이지 (/messages)
import NotificationsPage from "./routes/notifications";// 알림 페이지 (/notifications)
import MyProfilePage from "./routes/profile/index";    // 내 프로필 (/profile)
import UserProfilePage from "./routes/profile/$userId";// 다른 사용자 프로필 (/profile/:userId)
import NotFoundPage from "./routes/404";               // 404 에러 페이지 (/*)
import { AdminDashboard } from "./routes/admin/AdminDashboard.tsx"; // 관리자 페이지 (/dashboard)

// 🛣️ 라우터 설정 - 어떤 URL이 어떤 컴포넌트를 보여줄지 정의
// createBrowserRouter: HTML5 History API를 사용하는 브라우저 라우터 생성
// 이렇게 하면 URL이 변경될 때 페이지 새로고침 없이 컴포넌트만 바뀝니다
const router = createBrowserRouter([
  // ======= Layout 없이 보여줘야 하는 페이지들 (상단 네비게이션, 사이드바 없음) =======
  {
    path: "/login",          // URL이 "/login"일 때
    element: <LoginPage />,  // LoginPage 컴포넌트를 보여줌
  },
  {
    path: "/signup",         // URL이 "/signup"일 때  
    element: <SignupPage />, // SignupPage 컴포넌트를 보여줌
  },
  
  // ======= 공통 레이아웃이 적용되는 페이지들 (상단 네비게이션, 사이드바 포함) =======
  {
    path: "/",               // 루트 경로부터 시작하는 모든 URL
    element: <Layout />,     // Layout 컴포넌트가 감싸는 역할 (헤더, 사이드바 등)
    children: [              // Layout 안에서 보여질 자식 컴포넌트들
      // index: true는 부모 경로(/)와 정확히 일치할 때 보여줄 컴포넌트
      { index: true, element: <HomePage /> },  // "/" → 홈페이지
      
      // 각 경로는 부모 경로(/)에 자동으로 붙음
      { path: "explore", element: <ExplorePage /> },           // "/explore" → 탐색 페이지
      { path: "messages", element: <MessagesPage /> },         // "/messages" → 메시지 페이지
      { path: "notifications", element: <NotificationsPage /> }, // "/notifications" → 알림 페이지
      
      // 프로필 관련 라우트
      { path: "profile", element: <MyProfilePage /> },         // "/profile" → 내 프로필 페이지
      { path: "profile/:userId", element: <UserProfilePage /> }, // "/profile/123" → 특정 사용자 프로필 페이지
                                                               // :userId는 URL 파라미터 (동적 값)
      
      // 게시물 상세 페이지 (현재는 홈페이지로 연결됨)
      { path: "post/:id", element: <HomePage /> },             // "/post/456" → 게시물 상세 (임시로 홈페이지)
                                                               // :id는 게시물 ID 파라미터
      
      // 관리자 전용 페이지
      { path: "admin/dashboard", element: <AdminDashboard /> }, // "/admin/dashboard" → 관리자 대시보드
      
      // 404 페이지 - 위의 모든 경로와 일치하지 않는 URL
      { path: "*", element: <NotFoundPage /> },                // 정의되지 않은 모든 경로 → 404 페이지
    ],
  },
]);

// 🔧 React Query 클라이언트 생성
// API 호출, 캐싱, 로딩 상태 등을 관리하는 객체
const queryClient = new QueryClient();

// 🎯 메인 App 컴포넌트
// 이 컴포넌트가 React 앱의 시작점
const App = () => (
  <Provider store={store}>                             {/* Redux 전역 상태 관리 */}
    <QueryClientProvider client={queryClient}>         {/* React Query API 상태 관리 */}
      <Toaster position="bottom-right" richColors duration={3000} />
      <RouterProvider router={router} />               {/* 페이지 간 이동 라우팅 */}
    </QueryClientProvider>
  </Provider>
);

// 📤 App 컴포넌트를 다른 파일에서 사용할 수 있게 내보내기
export default App;
