import React from "react";
import { FaGithub, FaEnvelope, FaPhone } from "react-icons/fa";
import BasicLayout from "../layouts/BasicLayout.jsx";
import useCustomLogin from "../hooks/useCustomLogin.jsx";

const AboutPage = () => {
  const { isLogin, moveToLoginReturn } = useCustomLogin();

  if (!isLogin) {
    return moveToLoginReturn();
  }

  return (
    <BasicLayout>
      <br></br>
      <br></br>
      <div className="mt-20 py-6 px-6 flex flex-col items-center justify-center bg-gray-50 text-gray-800 w-[80%] max-w-screen-md mx-auto rounded-lg shadow-lg">
        {/* 제목 */}
        <h1 className="text-5xl font-extrabold mb-4 text-blue-600">
          Make My Day
        </h1>

        <br></br>

        {/* 소개글 */}
        <p className="text-lg text-gray-700 mb-10 text-center leading-relaxed">
          당신의 하루 일정을 더 특별하게!
          <br />
          일정 관리, 나만의 배경화면, 영감을 주는 명언까지 한 곳에서.
        </p>

        {/* 개발자 정보 */}
        <div className="flex flex-col items-center space-y-4">
          <div className="flex space-x-6">
            <a
              href="https://github.com/TheThinkingOne/MakeMyDay"
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center text-gray-800 hover:text-black"
            >
              <FaGithub size={30} />
              <span className="ml-2 text-lg">GitHub 오픈소스</span>
            </a>

            <div className="flex items-center text-gray-800">
              <FaEnvelope size={30} />
              <span className="ml-2 text-lg">ousterss@gmail.com</span>
            </div>
          </div>

          <div className="flex items-center text-gray-800">
            <FaPhone size={30} />
            <span className="ml-2 text-lg">010-6375-4553</span>
          </div>
        </div>

        {/* 푸터 */}
        <div className="mt-12 text-sm text-gray-500">
          © 2025 Make My Day. All Rights Reserved.
        </div>

        {/* 건의 게시판 버튼: 중앙 아래 고정 */}
        {/* 건의 게시판 버튼 */}
        <div className="flex justify-center mt-12">
          <button
            className="bg-blue-600 text-white px-6 py-2 rounded-md shadow-md hover:bg-blue-700"
            onClick={() => alert("건의 게시판 기능은 준비 중입니다!")}
          >
            ✉ 건의 게시판
          </button>
        </div>
        {/* */}
      </div>
    </BasicLayout>
  );
};

export default AboutPage;
