import React, { useState } from "react";
import ResultModal from "../common/ResultModal";
import { postAdd } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import useCustomLogin from "../../hooks/useCustomLogin";
import { showSuccess } from "../../util/toastUtil";

const initState = {
  title: "",
  dueDate: "",
  savePeriod: "",
  complete: false,
};

function AddComponent() {
  const [todo, setTodo] = useState({ ...initState });
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  const { moveToList } = useCustomMove();
  const { loginState } = useCustomLogin();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTodo((prev) => ({ ...prev, [name]: value }));
  };

  const handleDateValidation = () => {
    const selectedDate = new Date(todo.dueDate);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return selectedDate >= today;
  };

  const handleClicked = () => {
    if (!handleDateValidation()) {
      setError("마감일은 오늘 이후 날짜만 선택 가능합니다.");
      return;
    }

    if (!todo.savePeriod) {
      setError("보관기간을 선택해주세요.");
      return;
    }

    const newTodo = {
      ...todo,
      writer: loginState.userID,
    };

    postAdd(newTodo).then((res) => {
      setResult(res.tno);
      setTodo({ ...initState });
      setError("");
      showSuccess("일정이 등록되었습니다.");
    });
  };

  const closeModal = () => {
    setResult(null);
    moveToList();
  };

  return (
    <div className="flex flex-col items-center bg-gray-50 p-10 rounded-lg shadow-md w-full max-w-xl mx-auto">
      <h2 className="text-3xl font-extrabold text-blue-600 mb-10">일정 등록</h2>

      <Field label="일정 내용">
        <input
          type="text"
          name="title"
          value={todo.title}
          onChange={handleChange}
          className="w-full border p-3 rounded bg-white text-gray-800"
        />
      </Field>

      <Field label="마감일">
        <input
          type="date"
          name="dueDate"
          min={new Date().toISOString().split("T")[0]}
          value={todo.dueDate}
          onChange={handleChange}
          className="w-full border p-3 rounded bg-white text-gray-800"
        />
      </Field>

      <Field label="보관 기간">
        <select
          name="savePeriod"
          value={todo.savePeriod}
          onChange={handleChange}
          className="w-full border p-3 rounded bg-white text-gray-800"
        >
          <option value="" disabled>
            -- 선택하세요 --
          </option>
          <option value="ONE_DAY">하루</option>
          <option value="ONE_WEEK">일주일</option>
          <option value="PERMANENT">영구보관</option>
        </select>
      </Field>

      {error && <div className="text-red-500 text-center mb-4">{error}</div>}

      <div className="flex justify-center gap-4 mt-6">
        <button
          className="bg-blue-500 text-white px-6 py-2 rounded"
          onClick={handleClicked}
        >
          등록
        </button>
        <button
          className="bg-gray-400 text-white px-6 py-2 rounded"
          onClick={moveToList}
        >
          목록
        </button>
      </div>

      {result && (
        <ResultModal
          title="등록 완료"
          content={`일정 번호 ${result}가 등록되었습니다.`}
          callbackFn={closeModal}
        />
      )}
    </div>
  );
}

// ✅ 공통 필드 레이아웃 컴포넌트
const Field = ({ label, children }) => (
  <div className="flex w-full mb-4 items-center">
    <div className="w-1/4 text-right pr-4 font-bold text-gray-700">{label}</div>
    <div className="w-3/4">{children}</div>
  </div>
);

export default AddComponent;
