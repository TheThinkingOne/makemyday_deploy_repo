import React from "react";
import ReadComponent from "../../components/wallpapers/ReadComponent";
import { useNavigate, useParams } from "react-router-dom";

// 흠 근데 이게 필요한가 아냐 그냥 하자
function ReadPage(props) {
  const { ord } = useParams();
  const navigate = useNavigate();

  const handleModify = () => navigate(`/wallpaper/modify/${ord}`);

  return (
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold">배경화면 보기</div>
      <br></br>
      <ReadComponent ord={ord} />
      <div className="mt-4 flex gap-2">
        {/* 삭제 버튼은 API 호출과 confirm 처리 */}
      </div>
    </div>
  );
}

export default ReadPage;
