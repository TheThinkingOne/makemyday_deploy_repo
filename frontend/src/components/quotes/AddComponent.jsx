import React, { useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";
import { postAdd } from "../../api/quotesApi";
import ResultModal from "../common/ResultModal"; // 등록 결과를 보여줄 때 사용
import { showSuccess } from "../../util/toastUtil";
import useCustomLogin from "../../hooks/useCustomLogin";

const initState = {
  quotes: "",
  author: "",
};

function AddComponent() {
  const [quotesData, setQuotesData] = useState(initState);
  const [result, setResult] = useState(null);

  const { moveToList, moveToPath } = useCustomMove();

  // 🔹 인풋 변경 핸들러
  const handleChange = (e) => {
    const { name, value } = e.target;
    setQuotesData((prev) => ({ ...prev, [name]: value }));
  };

  // 🔹 등록 요청
  const handleClickAdd = () => {
    postAdd(quotesData).then((data) => {
      setResult("등록 완료되었습니다!");
      setQuotesData(initState);
      showSuccess("명언 등록이 완료되었습니다.");
    });
  };

  // 모달 닫기
  const { loginState } = useCustomLogin();

  const closeModal = () => {
    setResult(null);
    if (loginState?.roleNames?.includes("ADMIN")) {
      moveToList(); // 관리자만 리스트로 이동시키기
    } else {
      moveToPath("/"); // 일반 유저는 홈으로 이동
    }
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {result && (
        <ResultModal
          title="명언 등록 결과"
          content={result}
          callbackFn={closeModal}
        />
      )}

      <div className="flex justify-center">
        <div className="w-full max-w-2xl p-6">
          <div className="mb-4">
            <label className="block text-xl font-bold mb-2 text-black">
              명언
            </label>
            <input
              className="w-full p-4 border rounded text-gray-600"
              type="text"
              name="quotes"
              value={quotesData.quotes}
              onChange={handleChange}
              placeholder="명언을 입력하세요"
            />
          </div>

          <div className="mb-6">
            <label className="block text-xl font-bold mb-2 text-black">
              저자
            </label>
            <input
              className="w-full p-4 border rounded text-gray-600"
              type="text"
              name="author"
              value={quotesData.author}
              onChange={handleChange}
              placeholder="작성자 또는 저자명"
            />
          </div>

          <div className="flex justify-end">
            <button
              className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
              onClick={handleClickAdd}
            >
              등록하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddComponent;
