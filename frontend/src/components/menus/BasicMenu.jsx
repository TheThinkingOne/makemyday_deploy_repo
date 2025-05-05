import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import useCustomLogin from "../../hooks/useCustomLogin";

const BasicMenu = () => {
  const { loginState } = useCustomLogin();
  const isLogged = !!loginState?.userID;

  // 로그아웃 같은거 하면 상태 변화 감지해서 메뉴바 변경
  useEffect(() => {
    console.log("loginState 변경됨: ", loginState);
  }, [loginState]);

  return (
    <nav
      className="fixed top-4 left-0 w-full flex justify-between items-center px-6 z-50 bg-transparent transition-opacity duration-700"
      id="top-nav"
    >
      {/* 좌측 메뉴 */}
      <div className="flex gap-x-8 ml-4 text-white font-extrabold text-3xl drop-shadow-md">
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>
        {isLogged && (
          <>
            <Link to="/todo">나의 일정</Link>
            <Link to="/wallPaper">월페이퍼 관리</Link>
            {Array.isArray(loginState?.roleNames) &&
              loginState.roleNames.includes("ADMIN") && (
                <Link to="/quote/list">명언 관리</Link>
              )}
          </>
        )}
      </div>

      {/* 우측 메뉴 */}
      <div className="flex items-center space-x-8 mr-6 text-white text-2xl font-bold drop-shadow-md">
        {isLogged ? (
          <>
            <Link to="/member/modify" className="underline">
              {loginState.userName}
            </Link>
            <Link to="/member/logout" className="underline">
              로그아웃
            </Link>
          </>
        ) : (
          <Link to="/member/login" className="underline">
            로그인
          </Link>
        )}
      </div>
    </nav>
  );
};

export default BasicMenu;
