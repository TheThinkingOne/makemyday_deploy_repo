import React from "react";
import { Link } from "react-router-dom";
import BasicLayout from "../layouts/BasicLayout.jsx";
import RandomWallpaperAndQuote from "../components/main/RandomWallpaperAndQuotes.jsx";
import useCustomLogin from "../hooks/useCustomLogin.jsx";
import { FaGithub, FaEnvelope, FaPhone } from "react-icons/fa";

function MainPage() {
  const { isLogin } = useCustomLogin();

  // 로그인 안한 상태면 About 화면처럼 스타일 보이게하기
  if (!isLogin) {
    return (
      <BasicLayout>
        <div className="mt-20 py-11 px-6 flex flex-col items-center justify-center bg-gray-50 text-gray-800 w-[80%] max-w-screen-md mx-auto rounded-lg shadow-lg">
          <h1 className="text-5xl font-extrabold mb-4 text-blue-600">
            Make My Day
          </h1>

          <br></br>
          <p className="text-lg text-gray-700 mb-10 text-center leading-relaxed">
            로그인 후 나만의 배경화면과 명언을 확인할 수 있어요!
            <br />
            일정 관리, 감성 월페이퍼, 영감을 주는 명언까지 한 곳에서.
          </p>

          <div className="mt-12 py-0 text-sm text-gray-500">
            © 2025 Make My Day. All Rights Reserved.
          </div>

          <Link
            to="/member/login"
            className="mt-6 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
          >
            로그인/회원가입 하러 가기
          </Link>
        </div>
      </BasicLayout>
    );
  }

  // ✅ 로그인 한 경우: 월페이퍼 + 명언 + 등록 버튼
  return (
    <BasicLayout>
      <RandomWallpaperAndQuote />
      <div className="absolute bottom-10 right-10 z-10">
        <Link
          to="/quote/add"
          className="bg-blue-500 text-white px-4 py-2 rounded shadow hover:bg-blue-600"
        >
          ✨ 명언 등록하기
        </Link>
      </div>
    </BasicLayout>
  );
}

export default MainPage;
