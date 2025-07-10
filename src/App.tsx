// ========================================
// 🚀 App.tsx - React 애플리케이션의 메인 진입점
// ========================================
// 이 파일은 전체 React 앱의 루트 컴포넌트입니다.
// 모든 라우팅, 상태관리, API 클라이언트 설정이 여기서 이루어집니다.

// 📦 필요한 라이브러리들을 가져오기 (import)
import { QueryClient, QueryClientProvider } from "@tanstack/react-query"; // React Query
import { createBrowserRouter, RouterProvider } from "react-router-dom";   // React Router
import { Provider } from "react-redux"; // Redux
import { store } from "@/app/store";

// 🗂️ 페이지 컴포넌트들
import Layout from "./routes/_layout";
import HomePage from "./routes/index";
import LoginPage from "./routes/login";
import ExplorePage from "./routes/explore";
import MessagesPage from "./routes/messages";
import NotificationsPage from "./routes/notifications";
import MyProfilePage from "./routes/profile/index";
import UserProfilePage from "./routes/profile/$userId";
import PostDetailPage from "./routes/post/$id";
import NotFoundPage from "./routes/404";

// 🛣️ 라우터 설정
const router = createBrowserRouter([
  {
    path: "/login", // 로그인은 Layout 없이 렌더링
    element: <LoginPage />,
  },
  {
    path: "/", // 나머지는 Layout 적용
    element: <Layout />,
    children: [
      {
        index: true,
        element: <HomePage />,
      },
      {
        path: "explore",
        element: <ExplorePage />,
      },
      {
        path: "messages",
        element: <MessagesPage />,
      },
      {
        path: "notifications",
        element: <NotificationsPage />,
      },
      {
        path: "profile",
        element: <MyProfilePage />,
      },
      {
        path: "profile/:userId",
        element: <UserProfilePage />,
      },
      {
        path: "post/:id",
        element: <PostDetailPage />,
      },
      {
        path: "*",
        element: <NotFoundPage />,
      },
    ],
  },
]);

// 🔧 React Query 클라이언트 생성
const queryClient = new QueryClient();

// 🎯 App 컴포넌트
const App = () => {
  return (
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <RouterProvider router={router} />
        </QueryClientProvider>
      </Provider>
  );
};

export default App;
