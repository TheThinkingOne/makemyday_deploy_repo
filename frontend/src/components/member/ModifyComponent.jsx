import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { modifyMember } from "../../api/memberApi";
import useCustomLogin from "../../hooks/useCustomLogin";
import ResultModal from "../common/ResultModal";

const ModifyComponent = () => {
  const loginInfo = useSelector((state) => state.loginSlice);
  const { moveToLogin } = useCustomLogin();

  const [member, setMember] = useState({
    userID: "",
    userName: "",
    password: "",
    confirmPassword: "",
    isSocial: false,
  });

  const [result, setResult] = useState();
  const [error, setError] = useState("");

  useEffect(() => {
    if (loginInfo?.userID) {
      setMember({
        userID: loginInfo.userID,
        userName: loginInfo.userName,
        password: "",
        confirmPassword: "",
        isSocial: loginInfo.isSocial,
      });
    }
  }, [loginInfo]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setMember({ ...member, [name]: value });
  };

  const handleClickModify = async () => {
    setError("");

    if (!member.isSocial) {
      if (member.password.length < 8) {
        setError("비밀번호는 8자 이상이어야 합니다.");
        return;
      }
      if (member.password !== member.confirmPassword) {
        setError("비밀번호가 일치하지 않습니다.");
        return;
      }
    }

    try {
      await modifyMember({
        userID: member.userID,
        userName: member.userName,
        password: member.isSocial ? null : member.password,
        isSocial: member.isSocial,
      });
      setResult("회원 정보가 수정되었습니다.");
    } catch (err) {
      setError(
        "회원정보 수정 실패: " + (err.response?.data?.MESSAGE || err.message)
      );
    }
  };

  const closeModal = () => {
    setResult(null);
    moveToLogin();
  };

  if (!loginInfo?.userID) {
    return (
      <div className="text-center mt-10">🔄 사용자 정보를 불러오는 중...</div>
    );
  }

  return (
    <div className="flex justify-center mt-10">
      <div className="bg-gray-50 p-10 rounded-lg shadow-md w-full max-w-xl">
        <h2 className="text-3xl font-extrabold text-blue-600 mb-10 text-center">
          회원정보 변경하기
        </h2>

        {result && (
          <ResultModal
            callbackFn={closeModal}
            title={"회원 정보 수정"}
            content={"회원정보 수정 완료. 다시 로그인 해주세요."}
          />
        )}

        <FormField label="로그인 아이디" readOnly value={member.userID} />

        {!member.isSocial && (
          <>
            <FormField
              label="비밀번호"
              name="password"
              value={member.password}
              type="password"
              onChange={handleChange}
            />
            <FormField
              label="비밀번호 확인"
              name="confirmPassword"
              value={member.confirmPassword}
              type="password"
              onChange={handleChange}
            />
          </>
        )}

        <FormField
          label="닉네임"
          name="userName"
          value={member.userName}
          onChange={handleChange}
        />

        {error && <div className="text-red-500 text-center mb-4">{error}</div>}

        <div className="flex justify-center mt-6">
          <button
            type="button"
            className="bg-blue-500 text-white px-6 py-2 rounded"
            onClick={handleClickModify}
          >
            변경하기
          </button>
        </div>
      </div>
    </div>
  );
};

// 공통 필드 컴포넌트
const FormField = ({
  label,
  name,
  value,
  onChange,
  readOnly = false,
  type = "text",
}) => (
  <div className="flex flex-col mb-4">
    <label className="font-bold text-gray-700 mb-1">{label}</label>
    <input
      className={`border p-3 rounded text-gray-800 bg-white ${
        readOnly ? "bg-gray-100" : ""
      }`}
      name={name}
      type={type}
      value={value}
      onChange={onChange}
      readOnly={readOnly}
    />
  </div>
);

export default ModifyComponent;
