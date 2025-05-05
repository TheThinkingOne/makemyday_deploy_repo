import { useEffect, useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";
import { deleteOne, getOne } from "../../api/quotesApi";

const initState = {
  qno: 0,
  quotes: "",
  writer: "",
};

// 관리자만 입장 가능한 페이지 이므로 권한 설정해야 할텐데 권한 관련은 api에?

function AdminReadAndDeleteComponent({ qno }) {
  const [quotes, setQuotes] = useState(initState);

  const { moveToList } = useCustomMove();

  const handleClickDelete = () => {
    deleteOne(quotes.qno).then(() => {
      alert("삭제되었습니다.");
      moveToList();
    });
  };

  useEffect(() => {
    //
    getOne(qno).then((data) => {
      console.log(data);
      setQuotes(data);
    });
  }, [qno]);

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {makeDiv("번호", quotes.qno)}
      {makeDiv("명언", quotes.quotes)}

      {/* button...... start  */}
      <div className="flex justify-end p-4">
        <button
          type="button"
          className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
          onClick={() => moveToList()}
        >
          명언 목록으로
        </button>

        <button
          type="button"
          className="rounded p-4 m-2 text-xl w-32 text-white bg-red-500"
          onClick={handleClickDelete} // 해당 tno 에 해당하는 게시글 변경 페이지로 이동(함수호출이 아님)
        >
          명언 삭제
        </button>
      </div>
    </div>
  );
}

// 여기서 삭제를 그냥 다른 창으로 이동시키지 않고 그냥 삭제 시키는거로 바꿔야겠군

const makeDiv = (quotes, value) => (
  <div className="flex justify-center">
    <div className="relative mb-4 flex w-full flex-wrap items-stretch">
      <div className="w-1/5 p-6 text-right font-bold">{quotes}</div>
      <div className="w-4/5 p-6 rounded-r border border-solid shadow-md">
        {value}
      </div>
    </div>
  </div>
);

export default AdminReadAndDeleteComponent;
