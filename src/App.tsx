
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { Provider } from "react-redux";
import { store } from "@/app/store";

// 라우트 컴포넌트들을 직접 import
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

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        index: true,
        element: <HomePage />,
      },
      {
        path: "login",
        element: <LoginPage />,
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

const queryClient = new QueryClient();

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
