import React, { useEffect, useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";
import { getList } from "../../api/todoApi";
import PageComponent from "../common/PageComponent";

const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDto: null,
  prev: false,
  next: false,
  totalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0,
};

function ListComponent() {
  // ajax 통신은 useComponent, useEffect
  const { page, size, refresh, moveToList, moveToRead } = useCustomMove();

  const [serverData, setServerData] = useState(initState);

  useEffect(() => {
    getList({ page, size }).then((data) => {
      console.log(data);
      setServerData(data);
    });
  }, [page, size]); // 기본적으론 페이지나 사이즈가 변하지 않으면 서버 새로 호출 안함

  const isOverDueDate = (dueDate) => {
    return new Date(dueDate) < new Date();
  };

  return (
    <div className="border-2 border-blue-100 mt-10 mr-2 ml-2 max-w-[1200px] mx-auto">
      <div className="flex flex-wrap justify-center gap-4 w-full p-6">
        {serverData.dtoList.map((todo) => (
          <div
            key={todo.tno}
            className={`basis-[30%] min-w-[300px] p-4 rounded shadow-md cursor-pointer
              ${isOverDueDate(todo.dueDate) ? "bg-red-100" : "bg-white"}`}
            onClick={() => moveToRead(todo.tno)}
          >
            <div className="font-extrabold text-xl mb-2">{todo.title}</div>
            <div className="text-sm">마감일: {todo.dueDate}</div>
            <div className="text-sm">
              보관기간: {translateSavePeriod(todo.savePeriod)}
            </div>
            <div className="text-sm">
              상태: {todo.complete ? "완료됨" : "진행중"}
            </div>
          </div>
        ))}
      </div>

      <PageComponent serverData={serverData} movePage={moveToList} />
    </div>
  );
}

// 보관기간 한글 변환 함수
// 내 스프링 코드에서 Todo 엔티티에 savePeriod 는 enum 타입이고 이게 프론트로 넘어올때 영어 그대로 전달됨
const translateSavePeriod = (period) => {
  switch (period) {
    case "ONE_DAY":
      return "하루";
    case "ONE_WEEK":
      return "일주일";
    case "PERMANENT":
      return "영구보관";
    default:
      return "미지정";
  }
};

export default ListComponent;
