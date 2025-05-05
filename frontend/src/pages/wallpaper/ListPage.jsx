import React from "react";
import ListComponent from "../../components/wallpapers/ListComponent";
import { useNavigate } from "react-router-dom";

function ListPage(props) {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center justify-center min-h-[80vh] bg-white p-8">
      <div className="text-3xl font-extrabold mb-8 text-blue-500">
        배경화면 목록
      </div>
      <ListComponent />
    </div>
  );
}

export default ListPage;
