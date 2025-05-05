import React, { useEffect, useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";
import PageComponent from "../common/PageComponent";
import { getList } from "../../api/quotesApi";

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

// 관리자만 입장 가능한 페이지 이므로 권한 설정해야 할텐데 권한 관련은 api에?
function AdminListComponent(props) {
  const { page, size, refresh, moveToList, moveToRead } = useCustomMove();

  const [serverData, setServerData] = useState(initState);

  useEffect(() => {
    getList({ page, size }).then((data) => {
      console.log(data);
      setServerData(data);
    });
  }, [page, size, refresh]); // 기본적으론 페이지나 사이즈가 변하지 않으면 서버 새로 호출 안함

  return (
    <div className="border-2 border-blue-100 mt-10 mr-2 ml-2 max-w-[1200px] mx-auto">
      <div className="flex flex-wrap justify-center gap-4 w-full p-6">
        {serverData.dtoList.map((quotes) => (
          <div
            key={quotes.qno}
            className="basis-[30%] min-w-[300px] p-4 rounded shadow-md bg-white cursor-pointer"
            onClick={() => moveToRead(quotes.qno)}
          >
            <div className="flex justify-between text-gray-600">
              <div className="font-extrabold text-2xl p-2">{quotes.qno}</div>
              <div className="text-1xl m-1 p-2 font-extrabold">
                {quotes.quotes}
              </div>
              <div className="text-1xl m-1 p-2 font-medium">
                {quotes.author}
              </div>
            </div>
          </div>
        ))}
      </div>

      <PageComponent serverData={serverData} movePage={moveToList} />
    </div>
  );
}

export default AdminListComponent;
