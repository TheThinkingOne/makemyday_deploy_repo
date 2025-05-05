import LogoutComponent from "../../components/member/LogoutComponent.jsx";
import BasicMenu from "../../components/menus/BasicMenu.jsx";

const LogoutPage = () => {
  return (
    <div className="fixed top-0 left-0 z-[1055] flex flex-col h-full w-full">
      <BasicMenu />
      <div className="w-full flex justify-center items-center">
        <LogoutComponent />
      </div>
    </div>
  );
};

export default LogoutPage;
