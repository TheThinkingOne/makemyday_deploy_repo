import React, { useCallback } from "react";
import BasicLayout from "../../layouts/BasicLayout";
import { useNavigate, useSearchParams } from "react-router-dom";
import ListComponent from "../../components/todo/ListComponent";

const ListPage = () => {
  const navigate = useNavigate();
  const [queryParams] = useSearchParams();
  const page = queryParams.get("page") ? parseInt(queryParams.get("page")) : 1;
  const size = queryParams.get("size") ? parseInt(queryParams.get("size")) : 10;

  const handleClickAdd = useCallback(() => {
    navigate({ pathname: "/todo/add" });
  }, []);

  return (
    <BasicLayout>
      <div className="mt-10 py-6 px-6 flex flex-col items-center justify-center bg-gray-50 text-gray-800 w-[90%] max-w-screen-md mx-auto rounded-lg shadow-lg">
        {/* 제목 */}
        <h1 className="text-4xl font-extrabold mb-6 text-blue-600">
          나의 일정
        </h1>

        {/* 버튼 영역 */}
        <div className="mb-6 flex justify-center space-x-4">
          <button
            onClick={handleClickAdd}
            className="bg-blue-600 text-white px-4 py-2 rounded-md shadow hover:bg-blue-700"
          >
            + 일정 추가
          </button>
        </div>

        {/* 리스트 컴포넌트 */}
        <div className="w-full">
          <ListComponent />
        </div>
      </div>
    </BasicLayout>
  );
};

export default ListPage;
