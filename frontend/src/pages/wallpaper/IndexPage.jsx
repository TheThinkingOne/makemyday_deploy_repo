import { useCallback } from "react";
import { Outlet, useNavigate } from "react-router-dom";

const WallpaperIndexPage = () => {
  const navigate = useNavigate();

  const handleClickList = useCallback(() => {
    navigate("list");
  }, []);

  const handleClickAdd = useCallback(() => {
    navigate("add");
  }, []);

  return (
    <div className="p-4">
      <div className="flex gap-4 mb-4">
        {/* <div
          className="text-xl font-bold underline cursor-pointer"
          onClick={handleClickList}
        >
          LIST
        </div>
        <div
          className="text-xl font-bold underline cursor-pointer"
          onClick={handleClickAdd}
        >
          ADD
        </div> */}
      </div>
      <Outlet />
    </div>
  );
};

export default WallpaperIndexPage;
