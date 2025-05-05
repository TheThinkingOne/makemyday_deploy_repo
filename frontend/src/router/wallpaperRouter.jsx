import { lazy, Suspense } from "react";
import { Navigate } from "react-router-dom";

const Loading = <div>Loading...</div>;

// 월페이퍼 관련 Path 경로들
// 어 ㅅㅂ 리스트 페이지가 없다
const WallpaperList = lazy(() => import("../pages/wallpaper/ListPage.jsx"));

const WallpaperAdd = lazy(() => import("../pages/wallpaper/AddPage.jsx"));

const WallpaperRead = lazy(() => import("../pages/wallpaper/ReadPage.jsx"));

const WallpaperModify = lazy(() => import("../pages/wallpaper/ModifyPage.jsx"));

const WallpaperRouters = () => {
  return [
    {
      index: true,
      element: <Navigate to="list" />,
    },
    {
      path: "list",
      element: (
        <Suspense fallback={Loading}>
          <WallpaperList />
        </Suspense>
      ),
    },
    {
      path: "add",
      element: (
        <Suspense fallback={Loading}>
          <WallpaperAdd />
        </Suspense>
      ),
    },
    {
      path: "read/:ord",
      element: (
        <Suspense fallback={Loading}>
          <WallpaperRead />
        </Suspense>
      ),
    },
    {
      path: "modify/:ord",
      element: (
        <Suspense fallback={Loading}>
          <WallpaperModify />
        </Suspense>
      ),
    },
  ];
};

export default WallpaperRouters;
