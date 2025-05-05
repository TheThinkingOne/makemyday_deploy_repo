import React from "react";
import BasicLayout from "../../layouts/BasicLayout";
import RegisterComponent from "../../components/member/RegisterComponent";

const RegisterPage = () => {
  return (
    <BasicLayout>
      <div className=" text-3xl">Make My Day에 오신것을 환영합니다!!</div>
      {""}
      <div className="bg-white w-full mt-4 p-2">
        <RegisterComponent />
      </div>
      {""}
    </BasicLayout>
  );
};

export default RegisterPage;
