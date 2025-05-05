import React, { useEffect, useState } from "react";
import { getOne } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";

const initState = {
  tno: 0,
  title: "",
  dueDate: "",
  complete: false,
  savePeriod: "",
};

function ReadComponent({ tno }) {
  const [todo, setTodo] = useState(initState);
  const [loading, setLoading] = useState(true);
  const { moveToList, moveToModify } = useCustomMove();

  useEffect(() => {
    getOne(tno).then((data) => {
      setTodo(data);
      setLoading(false);
    });
  }, [tno]);

  if (loading) {
    return <div className="text-center text-xl mt-10">로딩 중...</div>;
  }

  return (
    <div className="flex flex-col items-center bg-gray-50 p-10 rounded-lg shadow-md w-full max-w-2xl mx-auto">
      <h2 className="text-3xl font-extrabold text-blue-600 mb-10">일정 확인</h2>

      <Field label="번호" value={todo.tno} />
      <Field label="제목" value={todo.title} />
      <Field label="마감일" value={todo.dueDate} />
      <Field
        label="상태"
        value={todo.complete ? "완료" : "미완료"}
        highlight={todo.complete ? "text-green-600" : "text-red-600"}
      />
      <Field label="보관 기간" value={translateSavePeriod(todo.savePeriod)} />

      <div className="flex justify-center gap-4 mt-6">
        <button
          className="rounded px-6 py-2 text-white bg-blue-500 text-lg"
          onClick={moveToList}
        >
          목록
        </button>
        <button
          className="rounded px-6 py-2 text-white bg-yellow-500 text-lg"
          onClick={() => moveToModify(todo.tno)}
        >
          수정
        </button>
      </div>
    </div>
  );
}

const Field = ({ label, value, highlight = "" }) => (
  <div className="flex w-full mb-4">
    <div className="w-1/4 text-right pr-4 font-bold text-gray-700">{label}</div>
    <div
      className={`w-3/4 border p-3 rounded bg-white ${highlight} text-black`}
    >
      {value}
    </div>
  </div>
);

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

export default ReadComponent;
