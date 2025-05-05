import React, { useRef, useState } from "react";
import { postAdd } from "../../api/wallpaperApi";
import { useMutation } from "@tanstack/react-query";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";
import { showSuccess } from "../../util/toastUtil";

const initState = {
  papertitle: "",
};

const AddComponent = () => {
  const [wallpaper, setWallpaper] = useState(initState);
  const [result, setResult] = useState(null);
  const uploadRef = useRef();
  const { moveToList } = useCustomMove();

  const addMutation = useMutation({
    mutationFn: (formData) => postAdd(formData),
    onSuccess: (data) => {
      setResult(data);
      setWallpaper(initState);
      uploadRef.current.value = null;
    },
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setWallpaper((prev) => ({ ...prev, [name]: value }));
  };

  const handleClickAdd = () => {
    const file = uploadRef.current.files[0];
    if (!file) {
      alert("파일을 선택해주세요.");
      return;
    }

    const allowedExtensions = ["jpg", "jpeg", "png", "bmp", "gif", "mp4"];
    const fileExtension = file.name.split(".").pop().toLowerCase();

    if (!allowedExtensions.includes(fileExtension)) {
      alert("지원하지 않는 파일 형식입니다.");
      return;
    }

    const formData = new FormData();
    formData.append("files", file);
    formData.append("papertitle", wallpaper.papertitle);

    addMutation.mutate(formData);
    showSuccess("월페이퍼가 등록되었습니다.");
  };

  const closeModal = () => {
    setResult(null);
    moveToList({ page: 1 });
  };

  return (
    <div className="flex flex-col items-center bg-gray-50 p-10 mt-10 rounded-lg shadow-md w-full max-w-xl mx-auto">
      <h2 className="text-3xl font-extrabold text-blue-600 mb-10">
        배경화면 등록
      </h2>

      <div className="w-full mb-4">
        <label className="block mb-2 font-bold text-gray-700 text-right pr-4">
          제목
        </label>
        <input
          type="text"
          name="papertitle"
          value={wallpaper.papertitle}
          onChange={handleChange}
          placeholder="배경화면 제목을 입력하세요"
          className="w-full border p-3 rounded text-gray-800 bg-white"
        />
      </div>

      <div className="w-full mb-4">
        <label className="block mb-2 font-bold text-gray-700 text-right pr-4">
          이미지 또는 동영상
        </label>
        <input
          type="file"
          ref={uploadRef}
          accept=".jpg,.jpeg,.png,.bmp,.gif,.mp4"
          className="w-full"
        />
      </div>

      <div className="flex justify-center gap-4 mt-6">
        <button
          onClick={handleClickAdd}
          className="bg-blue-500 hover:bg-blue-600 text-white px-6 py-2 rounded"
        >
          등록
        </button>
        <button
          onClick={() => moveToList({ page: 1 })}
          className="bg-gray-400 hover:bg-gray-500 text-white px-6 py-2 rounded"
        >
          목록
        </button>
      </div>

      {result && (
        <ResultModal
          title="등록 완료"
          content={`배경화면이 성공적으로 등록되었습니다.`}
          callbackFn={closeModal}
        />
      )}
    </div>
  );
};

export default AddComponent;
