import React from "react";
import AddComponent from "../../components/wallpapers/AddComponent.jsx";

function AddPage(props) {
  return (
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold text-gray-700">
        메인화면에 들어갈 월페이퍼를 등록하세요
      </div>
      <AddComponent />
    </div>
  );
}

export default AddPage;
