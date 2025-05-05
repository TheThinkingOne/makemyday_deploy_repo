import React, { useState, useEffect } from "react";
import { deleteOne, getOne, putOne } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";

const initState = {
  tno: 0,
  title: "",
  dueDate: "",
  savePeriod: "ONE_DAY",
  complete: false,
};

function ModifyComponent({ tno }) {
  const [todo, setTodo] = useState(initState);
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  const { moveToRead, moveToList } = useCustomMove();

  useEffect(() => {
    getOne(tno).then((data) => setTodo(data));
  }, [tno]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTodo((prev) => ({
      ...prev,
      [name]: name === "complete" ? value === "Y" : value,
    }));
  };

  const handleClickModify = () => {
    if (!validateDate()) {
      setError("마감일은 오늘 이후만 가능합니다.");
      return;
    }
    putOne(todo).then(() => setResult("수정 완료"));
  };

  const handleClickDelete = () => {
    deleteOne(tno).then(() => setResult("삭제 완료"));
  };

  const validateDate = () => {
    const selectedDate = new Date(todo.dueDate);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return selectedDate >= today;
  };

  const closeModal = () => {
    result === "삭제 완료" ? moveToList() : moveToRead(tno);
  };

  return (
    <div className="flex flex-col items-center bg-gray-50 p-10 rounded-lg shadow-md w-full max-w-2xl mx-auto">
      <h2 className="text-3xl font-extrabold text-blue-600 mb-10">일정 수정</h2>

      <Field label="번호" value={todo.tno} readOnly />
      <EditableField
        label="일정 내용"
        name="title"
        value={todo.title}
        onChange={handleChange}
      />
      <EditableField
        label="마감일"
        name="dueDate"
        type="date"
        value={todo.dueDate}
        onChange={handleChange}
        min={new Date().toISOString().split("T")[0]}
      />
      <SelectField
        label="보관 기간"
        name="savePeriod"
        value={todo.savePeriod}
        onChange={handleChange}
        options={[
          { value: "ONE_DAY", label: "하루" },
          { value: "ONE_WEEK", label: "일주일" },
          { value: "PERMANENT", label: "영구보관" },
        ]}
      />
      <SelectField
        label="상태"
        name="complete"
        value={todo.complete ? "Y" : "N"}
        onChange={handleChange}
        options={[
          { value: "Y", label: "완료됨" },
          { value: "N", label: "진행중" },
        ]}
      />

      {error && <div className="text-red-500 text-center mb-4">{error}</div>}

      <div className="flex justify-center gap-4 mt-6">
        <button
          onClick={handleClickDelete}
          className="bg-red-500 text-white px-6 py-2 rounded"
        >
          삭제
        </button>
        <button
          onClick={handleClickModify}
          className="bg-blue-500 text-white px-6 py-2 rounded"
        >
          수정
        </button>
      </div>

      {result && (
        <ResultModal
          title="처리결과"
          content={result}
          callbackFn={closeModal}
        />
      )}
    </div>
  );
}

const Field = ({ label, value, readOnly }) => (
  <div className="flex w-full mb-4">
    <div className="w-1/4 text-right pr-4 font-bold text-gray-700">{label}</div>
    <div
      className={`w-3/4 border p-3 rounded ${
        readOnly ? "bg-gray-100" : "bg-white"
      } text-gray-800`} // ✅ 글자색 추가
    >
      {value}
    </div>
  </div>
);

const EditableField = ({
  label,
  name,
  value,
  onChange,
  type = "text",
  min,
}) => (
  <div className="flex w-full mb-4">
    <div className="w-1/4 text-right pr-4 font-bold text-gray-700">{label}</div>
    <input
      name={name}
      value={value}
      onChange={onChange}
      type={type}
      min={min}
      className="w-3/4 border p-3 rounded bg-white text-gray-800" // 글자색 추가
    />
  </div>
);

const SelectField = ({ label, name, value, onChange, options }) => (
  <div className="flex w-full mb-4">
    <div className="w-1/4 text-right pr-4 font-bold text-gray-700">{label}</div>
    <select
      name={name}
      value={value}
      onChange={onChange}
      className="w-3/4 border p-3 rounded bg-white text-gray-800" // 글자색 추가
    >
      {options.map((opt) => (
        <option key={opt.value} value={opt.value}>
          {opt.label}
        </option>
      ))}
    </select>
  </div>
);

export default ModifyComponent;
