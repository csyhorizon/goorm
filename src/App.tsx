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

// 🛣️ 라우터 설정 - 어떤 URL이 어떤 컴포넌트를 보여줄지 정의
const router = createBrowserRouter([
  // Layout 없이 보여줘야 하는 페이지들
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/signup",
    element: <SignupPage />,
  },
  // 공통 레이아웃이 적용되는 페이지들
  {
    path: "/",

    element: <Layout />,
    children: [
      { index: true, element: <HomePage /> },
      { path: "explore", element: <ExplorePage /> },
      { path: "messages", element: <MessagesPage /> },
      { path: "notifications", element: <NotificationsPage /> },
      { path: "profile", element: <MyProfilePage /> },
      { path: "profile/:userId", element: <UserProfilePage /> },
      { path: "post/:id", element: <HomePage /> }, 
      { path: "*", element: <NotFoundPage /> },
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
      <RouterProvider router={router} />               {/* 페이지 간 이동 라우팅 */}
      <Toaster position="bottom-right" richColors duration={3000} />
    </QueryClientProvider>
  </Provider>
);

// 📤 App 컴포넌트를 다른 파일에서 사용할 수 있게 내보내기
export default App;
