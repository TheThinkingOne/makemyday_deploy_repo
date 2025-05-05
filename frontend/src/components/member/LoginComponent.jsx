import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { login, loginPostAsync } from "../../slices/loginSlice";
import { loginPost } from "../../api/memberApi";
import { useNavigate } from "react-router-dom";
import useCustomLogin from "../../hooks/useCustomLogin";
import KaKaoLoginComponent from "./KakaoLoginComponent.jsx";

const initState = {
  userID: "",
  password: "",
};

function LoginComponent(props) {
  const [loginParam, setLoginParam] = useState({ ...initState }); // 이메일로그인(oauth)

  const { doLogin, moveToPath } = useCustomLogin();

  // const dispatch = useDispatch();

  // const navigate = useNavigate();

  // useSelector 와 useDispatch 공부하기
  // dispatch의 내용은 다음에 이 어플리케이션에서 이 데이터를 이렇게 유지해 달라는 다음 데이터

  const handleChange = (e) => {
    loginParam[e.target.name] = e.target.value;

    setLoginParam({ ...loginParam }); // 새로운 객체 만들기
  };

  const handleClickLogin = (e) => {
    //dispatch(login(loginParam));

    doLogin(loginParam).then((data) => {
      if (data.error) {
        alert("이메일과 패스워드를 확인해주세요!");
      } else {
        moveToPath("/");
      }
    });

    // 이 아래껀 로그인 로그인 훅스 적용 전
    // dispatch(loginPostAsync(loginParam))
    //   .unwrap()
    //   .then((data) => {
    //     console.log("after unwrap");
    //     console.log(data); // 비동기 로그인에 쓰는 unwrap
    //     if (data.error) {
    //       alert("이메일과 패스워드를 확인해주세요");
    //     } else {
    //       alert("로그인에 성공했습니다");
    //       navigate({ pathname: "/" }, { replace: true }); // 이렇게 하면 로그인 후에 뒤로가기 막힘
    //     }
    //   }); // 요즘음 createAsyncthunk 설정한 메소드 바로 사용
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      <div className="flex justify-center">
        <div className="text-4xl m-4 p-4 font-extrabold text-sky-300">
          Make My Day 로그인
        </div>
      </div>

      <div className="flex flex-col items-center mb-6">
        <label className="text-lg font-bold mb-2 text-center w-full">
          User ID
        </label>
        <input
          className="min-w-[250px] p-3 rounded border border-neutral-500 shadow-md text-center text-black"
          name="userID"
          type="text"
          value={loginParam.userID}
          onChange={handleChange}
        />
      </div>

      <div className="flex flex-col items-center mb-6">
        <label className="text-lg font-bold mb-2 text-center w-full">
          Password
        </label>
        <input
          className="min-w-[250px] p-3 rounded border border-neutral-500 shadow-md text-center text-black"
          name="password"
          type="password"
          value={loginParam.password}
          onChange={handleChange}
        />
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full justify-center">
          <div className="w-2/5 p-6 flex justify-center font-bold">
            <button
              className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
              onClick={handleClickLogin}
            >
              로그인
            </button>
          </div>
        </div>
      </div>

      {/* 🔽 회원가입 버튼 추가 */}
      <div className="flex justify-center">
        <button
          className="rounded p-3 w-36 bg-gray-500 text-white text-lg"
          onClick={() => moveToPath("/member/register")}
        >
          회원가입
        </button>
      </div>

      <br></br>

      {/* 2025/02/19 카카오 로그인 추가 */}
      <KaKaoLoginComponent />
    </div>
  );
}

export default LoginComponent;
