import { useParams } from "react-router-dom";
import React from "react";
import AdminReadAndDeleteComponent from "../../components/quotes/AdminReadAndDeleteComponent";

const AdminReadAndDeletePage = () => {
  const { qno } = useParams();

  console.log("qno: ", qno);

  // 컴포넌트 중심으로 개편

  return (
    <div className="font-extrabold w-full bg-white mt-6">
      <div className="text-2xl"> 명언 관리 {qno}</div>

      <AdminReadAndDeleteComponent qno={qno} />
    </div>
  );
};

export default AdminReadAndDeletePage;
