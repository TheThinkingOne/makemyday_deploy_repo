import React from "react";
import { useParams } from "react-router-dom";
import ModifyComponent from "../../components/wallpapers/ModifyComponent";

function ModifyPage(props) {
  const { ord } = useParams();
  return (
    <div className="p-4 w-full bg-white">
      <ModifyComponent ord={ord}></ModifyComponent>
    </div>
  );
}

export default ModifyPage;
