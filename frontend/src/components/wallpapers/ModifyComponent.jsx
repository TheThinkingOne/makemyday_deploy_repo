import { useState, useRef, useEffect } from "react";
import { getOne, modifyOne, deleteOne } from "../../api/wallpaperApi";
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";
import { API_SERVER_HOST } from "../../api/todoApi";

const ModifyComponent = ({ ord }) => {
  const [wallpaper, setWallpaper] = useState(null);
  const [preview, setPreview] = useState(null);
  const [result, setResult] = useState(null);
  const uploadRef = useRef();
  const { moveToList, moveToRead } = useCustomMove();

  useEffect(() => {
    getOne(ord).then(setWallpaper);
  }, [ord]);

  const handleFileChange = () => {
    const file = uploadRef.current.files[0];
    if (file) {
      setPreview(URL.createObjectURL(file));
    }
  };

  const handleClickModify = async () => {
    const file = uploadRef.current.files[0];

    if (file) {
      const allowedExtensions = ["jpg", "jpeg", "png", "bmp", "gif", "mp4"];
      const fileExtension = file.name.split(".").pop().toLowerCase();

      if (!allowedExtensions.includes(fileExtension)) {
        alert("지원하지 않는 파일 형식입니다.");
        return;
      }
    }

    const formData = new FormData();
    formData.append("paperTitle", wallpaper.paperTitle);

    if (file) {
      formData.append("files", file);
    } else {
      wallpaper.uploadFileNames.forEach((fileName) =>
        formData.append("uploadFileNames", fileName)
      );
    }

    await modifyOne(ord, formData);
    setResult("수정 완료");
  };

  const handleClickDelete = async () => {
    await deleteOne(ord);
    setResult("삭제 완료");
  };

  const closeModal = () => {
    result === "삭제 완료" ? moveToList() : moveToRead(ord);
  };

  if (!wallpaper) return <div className="text-white">로딩 중...</div>;

  return (
    <div className="p-4 text-gray-800 bg-white rounded shadow-md max-w-5xl mx-auto my-10">
      <h2 className="text-2xl font-bold text-center text-blue-600 mb-6">
        배경화면 수정
      </h2>

      <div className="mb-6">
        <label className="block font-bold mb-2 text-gray-700">
          배경화면 제목
        </label>
        <input
          type="text"
          value={wallpaper.paperTitle}
          onChange={(e) =>
            setWallpaper({ ...wallpaper, paperTitle: e.target.value })
          }
          className="border p-2 w-full rounded text-gray-800 bg-white"
        />
      </div>

      {/* 이미지 두 개를 좌우로 배치 */}
      <div className="mb-6 flex flex-col md:flex-row gap-4 justify-center items-center">
        {/* 기존 이미지 */}
        <div className="w-full md:w-1/2 text-center">
          <h3 className="text-gray-700 font-bold mb-2">기존 사진</h3>
          <img
            src={`${API_SERVER_HOST}/makemyday/wallpaper/view/${wallpaper.uploadFileNames[0]}`}
            alt="current"
            className="w-full h-48 object-cover rounded"
          />
        </div>

        {/* 미리보기 이미지 */}
        <div className="w-full md:w-1/2 text-center">
          <h3 className="text-gray-700 font-bold mb-2">
            변경하려는 사진 (미리보기)
          </h3>
          {preview ? (
            <img
              src={preview}
              alt="preview"
              className="w-full h-48 object-cover rounded mb-2"
            />
          ) : (
            <p className="text-gray-500 mb-2">이미지를 선택하세요.</p>
          )}
          <input
            type="file"
            ref={uploadRef}
            accept=".jpg,.jpeg,.png,.bmp,.gif,.mp4"
            onChange={handleFileChange}
            className="w-full text-gray-800"
          />
        </div>
      </div>

      {/* 버튼 영역 */}
      <div className="flex gap-4 justify-center mt-6">
        <button
          onClick={handleClickModify}
          className="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600"
        >
          수정
        </button>
        <button
          onClick={handleClickDelete}
          className="bg-red-500 text-white px-6 py-2 rounded hover:bg-red-600"
        >
          삭제
        </button>
        <button
          onClick={() => moveToList({ page: 1 })}
          className="bg-gray-500 text-white px-6 py-2 rounded hover:bg-gray-600"
        >
          목록으로
        </button>
      </div>

      {result && (
        <ResultModal
          title="처리 결과"
          content={result}
          callbackFn={closeModal}
        />
      )}
    </div>
  );
};

export default ModifyComponent;
