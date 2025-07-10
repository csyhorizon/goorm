
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

// 🗂️ 각 페이지 컴포넌트들을 가져오기
// 파일 기반 라우팅: routes 폴더의 파일들이 자동으로 페이지가 됨
import Layout from "./routes/_layout";           // 공통 레이아웃 (사이드바, 헤더 등)
import HomePage from "./routes/index";           // 홈페이지 (/)
import LoginPage from "./routes/login";          // 로그인 페이지 (/login)
import ExplorePage from "./routes/explore";      // 탐색 페이지 (/explore)
import MessagesPage from "./routes/messages";    // 메시지 페이지 (/messages)
import NotificationsPage from "./routes/notifications"; // 알림 페이지 (/notifications)
import MyProfilePage from "./routes/profile/index";     // 내 프로필 (/profile)
import UserProfilePage from "./routes/profile/$userId"; // 다른 사용자 프로필 (/profile/:userId)
import PostDetailPage from "./routes/post/$id";         // 포스트 상세 (/post/:id)
import NotFoundPage from "./routes/404";                // 404 에러 페이지 (/*)

// 🛣️ 라우터 설정 - 어떤 URL이 어떤 컴포넌트를 보여줄지 정의
const router = createBrowserRouter([
  {
    // 최상위 경로 "/" - 모든 페이지의 공통 레이아웃
    path: "/",
    element: <Layout />, // Layout 컴포넌트가 모든 페이지의 기본 틀이 됨
    children: [ // Layout 안에 들어갈 하위 페이지들
      {
        index: true, // "/" 경로 (홈페이지)
        element: <HomePage />,
      },
      {
        path: "login", // "/login" 경로
        element: <LoginPage />,
      },
      {
        path: "explore", // "/explore" 경로
        element: <ExplorePage />,
      },
      {
        path: "messages", // "/messages" 경로
        element: <MessagesPage />,
      },
      {
        path: "notifications", // "/notifications" 경로
        element: <NotificationsPage />,
      },
      {
        path: "profile", // "/profile" 경로 (내 프로필)
        element: <MyProfilePage />,
      },
      {
        path: "profile/:userId", // "/profile/123" 같은 동적 경로
        // :userId는 URL 파라미터 (실제 사용자 ID로 대체됨)
        element: <UserProfilePage />,
      },
      {
        path: "post/:id",
        element: <HomePage />, 
      },
      {
        path: "*", // 위의 모든 경로에 해당하지 않는 경우 (404 페이지)
        element: <NotFoundPage />,
      },
    ],
  },
]);

// 🔧 React Query 클라이언트 생성
// API 호출, 캐싱, 로딩 상태 등을 관리하는 객체
const queryClient = new QueryClient();

// 🎯 메인 App 컴포넌트
// 이 컴포넌트가 React 앱의 시작점
const App = () => {
  return (
    // Redux Provider: 전역 상태를 모든 컴포넌트에서 사용할 수 있게 함
    <Provider store={store}>
      {/* React Query Provider: API 호출 관련 기능을 모든 컴포넌트에서 사용할 수 있게 함 */}
      <QueryClientProvider client={queryClient}>
        {/* Router Provider: 페이지 간 이동 기능을 제공 */}
        <RouterProvider router={router} />
      </QueryClientProvider>
    </Provider>
  );
};

// 📤 App 컴포넌트를 다른 파일에서 사용할 수 있게 내보내기
export default App;
