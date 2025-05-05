import { useQuery } from "@tanstack/react-query";
import { getList } from "../../api/wallpaperApi";
import useCustomMove from "../../hooks/useCustomMove";
import PageComponent from "../common/PageComponent";
import { API_SERVER_HOST } from "../../api/todoApi";

API_SERVER_HOST;

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

const ListComponent = () => {
  const { page, size, moveToList, moveToRead, moveToRegister } =
    useCustomMove();

  const { data } = useQuery({
    queryKey: ["wallpapers/list", { page, size }],
    queryFn: () => getList({ page, size }),
    keepPreviousData: true,
    staleTime: 1000 * 30,
  });

  const serverData = data || initState;

  return (
    <div className="relative w-full max-w-7xl">
      {/* 그리드 영역 */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 px-4">
        {serverData.dtoList.map((wallpaper) => (
          <div
            key={wallpaper.ord}
            className="border p-2 cursor-pointer shadow-md rounded"
            onClick={() => moveToRead(wallpaper.ord)}
          >
            <img
              src={`${API_SERVER_HOST}/makemyday/wallpaper/view/${wallpaper.uploadFileNames[0]}`}
              alt="wallpaper"
              className="w-full h-48 object-cover mb-2 rounded"
            />
            <div className="text-center font-bold">{wallpaper.paperTitle}</div>
          </div>
        ))}
      </div>

      {/* 페이지네이션 */}
      <div className="mt-8 flex justify-center">
        <PageComponent serverData={serverData} movePage={moveToList} />
      </div>

      {/* 고정된 등록 버튼 */}
      <button
        onClick={() => moveToRegister()}
        className="fixed bottom-8 right-8 bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded shadow-lg transition"
      >
        + 새 월페이퍼 작성
      </button>
    </div>
  );
};

export default ListComponent;
