// RouterLayout.jsx (새로 추가)
import { Outlet } from "react-router-dom";
import BasicMenu from "../components/menus/BasicMenu";

function RouterLayout() {
  return (
    <div className="w-full h-screen">
      <BasicMenu />
      <main className="w-full h-full">
        <Outlet />
      </main>
    </div>
  );
}

export default RouterLayout;
