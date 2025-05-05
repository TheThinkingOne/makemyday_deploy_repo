import { useState } from "react";
import {
  createSearchParams,
  useNavigate,
  useSearchParams,
} from "react-router-dom";

const getNum = (param, defaultValue) => {
  if (!param) {
    return defaultValue;
  }

  return parseInt(param);
};

const useCustomMove = () => {
  const navigate = useNavigate();

  const [refresh, setRefresh] = useState(false);

  // 이동하는 기능
  const [queryParams] = useSearchParams();

  // 재사용하자
  const page = getNum(queryParams.get("page"), 1);
  const size = getNum(queryParams.get("size"), 10);

  // page = 3 & size = 10 이런식
  const queryDefault = createSearchParams({ page, size }).toString();

  const moveToList = (pageParam) => {
    let queryStr = "";

    if (pageParam) {
      const pageNum = getNum(pageParam.page, 1);
      const sizeNum = getNum(pageParam.size, 10);

      queryStr = createSearchParams({
        page: pageNum,
        size: sizeNum,
      }).toString();
    } else {
      queryStr = queryDefault;
    }

    setRefresh(!refresh);

    navigate({ pathname: "../list", search: queryStr });

    //
  };

  const moveToModify = (num) => {
    navigate({
      pathname: `../modify/${num}`,
      search: queryDefault, // 쿼리 스트링 계속 유지
    });
  };

  const moveToRead = (num) => {
    navigate({
      pathname: `../read/${num}`,
      search: queryDefault, // 쿼리 스트링 계속 유지
    });
  };

  const moveToPath = (path) => {
    navigate({ pathname: path });
  };

  const moveToRegister = () => {
    navigate({ pathname: "../add", search: queryDefault });
  };

  const moveToHome = () => {
    navigate({ pathname: "/" });
  };

  return {
    moveToList,
    moveToModify,
    moveToRead,
    moveToHome,
    moveToPath,
    moveToRegister,
    page,
    size,
    refresh,
  };
};

export default useCustomMove;
