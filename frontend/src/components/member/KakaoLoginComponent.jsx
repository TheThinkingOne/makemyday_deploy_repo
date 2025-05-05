import React from "react";
import { getKakaoLoginLink } from "../../api/kakaoApi";
import { Link } from "react-router-dom";

const link = getKakaoLoginLink();

function KaKaoLoginComponent(props) {
  return (
    <div className="flex flex-col">
      <div className="text-center text-gray-800">
        카카오 로그인 시 자동으로 가입처리 됩니다
      </div>
      <div className="flex justify-center w-full">
        <div
          className="text-3xl text-center m-6 text-white font-extrabold w-3/4 bg-yellow-500
        shadow-sm rounded p-2"
        >
          <Link to={link}>KAKAO LOGIN</Link>
        </div>
      </div>
    </div>
  );
}

export default KaKaoLoginComponent;
