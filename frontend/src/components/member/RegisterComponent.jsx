import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerMember, checkDuplicateID } from "../../api/memberApi";

const initState = {
  userID: "",
  password: "",
  confirmPassword: "",
  userName: "",
};

const RegisterComponent = () => {
  const [form, setForm] = useState({ ...initState });
  const [checkMessage, setCheckMessage] = useState("");
  const [validationError, setValidationError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const validate = () => {
    if (form.userID.length < 8) {
      setValidationError("아이디는 8자 이상이어야 합니다.");
      return false;
    }
    if (form.password.length < 8) {
      setValidationError("비밀번호는 8자 이상이어야 합니다.");
      return false;
    }
    if (form.password !== form.confirmPassword) {
      setValidationError("비밀번호가 일치하지 않습니다.");
      return false;
    }
    return true;
  };

  const handleRegister = async () => {
    setValidationError("");
    if (!validate()) return;

    try {
      const result = await registerMember(form);
      alert("회원가입 성공!");
      navigate("/member/login");
    } catch (err) {
      alert("회원가입 실패: " + (err.response?.data?.MESSAGE || err.message));
    }
  };

  const handleCheckID = async () => {
    if (form.userID.length < 8) {
      setCheckMessage("❌ 아이디는 최소 8자 이상이어야 합니다.");
      return;
    }

    try {
      const result = await checkDuplicateID(form.userID);
      if (result.result === "DUPLICATE") {
        setCheckMessage("❌ 이미 사용 중인 아이디입니다.");
      } else {
        setCheckMessage("✅ 사용 가능한 아이디입니다!");
      }
    } catch (err) {
      setCheckMessage("⚠️ 중복 확인 실패");
    }
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      <div className="flex justify-center">
        <div className="text-4xl m-4 p-4 font-extrabold text-blue-500">
          Make My Day에 오신것을 환영합니다!!
        </div>
      </div>

      <div className="text-center text-xl font-bold mb-6">회원가입</div>

      {/* 아이디 */}
      <div className="flex flex-col items-center mb-4">
        <label className="text-md font-bold mb-1 w-full text-left pl-2">
          아이디
        </label>
        <div className="flex w-full justify-center">
          <input
            className="min-w-[250px] p-2 rounded border border-neutral-500 shadow-sm text-center"
            type="text"
            name="userID"
            value={form.userID}
            onChange={handleChange}
          />
          <button
            className="ml-2 bg-gray-400 text-white p-2 rounded w-28 text-sm"
            onClick={handleCheckID}
          >
            사용 가능 확인
          </button>
        </div>
        {checkMessage && (
          <div className="text-sm mt-2 text-blue-700">{checkMessage}</div>
        )}
      </div>

      {/* 비밀번호 */}
      <div className="flex flex-col items-center mb-4">
        <label className="text-md font-bold mb-1 w-full text-left pl-2">
          비밀번호
        </label>
        <input
          className="min-w-[250px] p-2 rounded border border-neutral-500 shadow-sm text-center"
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
        />
      </div>

      {/* 비밀번호 확인 */}
      <div className="flex flex-col items-center mb-4">
        <label className="text-md font-bold mb-1 w-full text-left pl-2">
          비밀번호 확인
        </label>
        <input
          className="min-w-[250px] p-2 rounded border border-neutral-500 shadow-sm text-center"
          type="password"
          name="confirmPassword"
          value={form.confirmPassword}
          onChange={handleChange}
        />
      </div>

      {/* 닉네임 */}
      <div className="flex flex-col items-center mb-6">
        <label className="text-md font-bold mb-1 w-full text-left pl-2">
          닉네임
        </label>
        <input
          className="min-w-[250px] p-2 rounded border border-neutral-500 shadow-sm text-center"
          type="text"
          name="userName"
          value={form.userName}
          onChange={handleChange}
        />
      </div>

      {/* 유효성 검사 메시지 */}
      {validationError && (
        <div className="text-sm text-red-500 text-center mb-2">
          {validationError}
        </div>
      )}

      {/* 가입 버튼 */}
      <div className="flex justify-center">
        <button
          className="bg-blue-500 text-white text-lg font-bold rounded p-2 w-36"
          onClick={handleRegister}
        >
          회원가입
        </button>
      </div>
    </div>
  );
};

export default RegisterComponent;
