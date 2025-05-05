import React from "react";
import BasicMenu from "../components/menus/BasicMenu";

function BasicLayout({ children }) {
  return (
    <div className="w-full min-h-screen bg-blue-300 overflow-y-auto">
      <BasicMenu />
      <main className="w-full min-h-[calc(100vh-4rem)] px-4 py-10">
        {children}
      </main>
    </div>
  );
}

export default BasicLayout;
