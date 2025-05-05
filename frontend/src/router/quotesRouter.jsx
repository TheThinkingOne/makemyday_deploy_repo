import { lazy, Suspense } from "react";
import { Navigate } from "react-router-dom";

const Loading = <div>Loading....</div>;

const QuotesAdd = lazy(() => import("../pages/quotes/AddPage.jsx"));
const QuotesList = lazy(() => import("../pages/quotes/AdminListPage.jsx"));
const QuotesReadAndDelete = lazy(() =>
  import("../pages/quotes/AdminReadAndDeletePage.jsx")
);

const quotesRouter = () => {
  return [
    {
      index: true, // 기본 경로 설정
      element: <Navigate to="add" />,
    },
    {
      path: "add",
      element: (
        <Suspense fallback={Loading}>
          <QuotesAdd />
        </Suspense>
      ),
    },
    {
      path: "list",
      element: (
        <Suspense fallback={Loading}>
          <QuotesList />
        </Suspense>
      ),
    },
    {
      path: "read/:qno", // 경로 정리
      element: (
        <Suspense fallback={Loading}>
          <QuotesReadAndDelete />
        </Suspense>
      ),
    },
  ];
};

export default quotesRouter;
