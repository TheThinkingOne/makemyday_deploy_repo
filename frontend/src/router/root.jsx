import { createBrowserRouter } from "react-router-dom";
import { Suspense, lazy } from "react";
import todoRouter from "./todoRouter.jsx";
//import productRouters from "./ProductsRouter.jsx";
import memberRouter from "./memberRouter.jsx";
import wallpaperRouter from "./wallpaperRouter.jsx";
import BasicLayout from "../layouts/BasicLayout.jsx";
import RouterLayout from "../layouts/RouterLayout.jsx";
import WallpaperIndexPage from "../pages/wallpaper/IndexPage.jsx";
import quotesRouter from "./quotesRouter.jsx";

const Loading = <div>Loading....</div>;

const Main = lazy(() => import("../pages/MainPage.jsx"));
const About = lazy(() => import("../pages/AboutPage.jsx"));
const TodoIndex = lazy(() => import("../pages/todo/IndexPage.jsx"));
//const ProductsIndex = lazy(() => import("../pages/products/IndexPage.jsx"));

const root = createBrowserRouter([
  {
    path: "",
    element: (
      <Suspense fallback={Loading}>
        <Main />
      </Suspense>
    ),
  },
  {
    path: "about",
    element: (
      <Suspense fallback={Loading}>
        <About />
      </Suspense>
    ),
  },
  {
    path: "todo",
    element: (
      <Suspense fallback={Loading}>
        <TodoIndex />
      </Suspense>
    ),
    children: todoRouter(), // 'children'으로 수정
  },
  {
    path: "wallpaper",
    element: (
      <Suspense fallback={Loading}>
        <RouterLayout />
      </Suspense>
    ),
    children: [
      {
        path: "",
        element: (
          <Suspense fallback={Loading}>
            <WallpaperIndexPage />
          </Suspense>
        ),
        children: wallpaperRouter(), // 그대로 사용
      },
    ],
  },
  {
    path: "member",
    children: memberRouter(),
  },
  {
    path: "quote",
    children: quotesRouter(), // 요렇게 간단하게 처리
  },
]);

export default root;
