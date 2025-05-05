import React from "react";
import AdminListComponent from "../../components/quotes/AdminListComponent";

const AdminListPage = () => {
  return (
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold text-blue-500">
        등록된 명언 리스트
      </div>

      <AdminListComponent />
    </div>
  );
};

export default AdminListPage;
