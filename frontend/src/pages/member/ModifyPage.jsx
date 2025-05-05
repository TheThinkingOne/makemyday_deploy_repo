import React from "react";
import ModifyComponent from "../../components/member/ModifyComponent";
import BasicLayout from "../../layouts/BasicLayout.jsx";

const ModifyPage = () => {
  return (
    <BasicLayout>
      <br></br>
      {""}
      <div className="bg-white w-full mt-4 p-2">
        <ModifyComponent />
      </div>
      {""}
    </BasicLayout>
  );
};

export default ModifyPage;
