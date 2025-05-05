import React, { useEffect, useState, useRef } from "react";
import { getRandomMainData } from "../../api/randomQuotesAndWallpaperAtMainPageApi";

const RandomWallpaperAndQuote = () => {
  const [mainData, setMainData] = useState(null);
  const [showUI, setShowUI] = useState(true);

  const INACTIVITY_TIME = 10000; // 10초 비활동 시 UI 숨김
  const inactivityTimer = useRef(null); // useRef로 타이머 관리 (렌더링 방지)

  // 사용자 활동 감지 핸들러
  const activityHandler = () => {
    setShowUI(true);
    clearTimeout(inactivityTimer.current);
    inactivityTimer.current = setTimeout(
      () => setShowUI(false),
      INACTIVITY_TIME
    );
  };

  useEffect(() => {
    const fetchAndSet = async () => {
      try {
        const data = await getRandomMainData();
        setMainData(data);
      } catch (e) {
        console.error("등록된 명언이나 월페이퍼가 없습니다.", e);
      }
    };

    const topNav = document.getElementById("top-nav");
    if (topNav) {
      topNav.style.opacity = showUI ? "1" : "0";
    }

    fetchAndSet();
    const interval = setInterval(fetchAndSet, 10000);

    // 이벤트 등록
    window.addEventListener("mousemove", activityHandler);
    window.addEventListener("keydown", activityHandler);

    // 최초 타이머 시작
    inactivityTimer.current = setTimeout(
      () => setShowUI(false),
      INACTIVITY_TIME
    );

    return () => {
      clearInterval(interval);
      window.removeEventListener("mousemove", activityHandler);
      window.removeEventListener("keydown", activityHandler);
      clearTimeout(inactivityTimer.current);
    };
  }, [showUI]);

  if (!mainData) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-900 text-white">
        <div className="animate-pulse text-2xl">로딩 중...</div>
      </div>
    );
  }

  const quote = mainData.quote || {
    quotes: "아직 등록된 명언이 없습니다. 모두 함께 만들어가요!",
    author: "You",
  };

  // 이 부분 주소 잘못 적혀있었음
  const imageURL = mainData.wallpaper
    ? `/makemyday/wallpaper/view/${mainData.wallpaper.uploadFileNames[0]}`
    : null;

  return (
    <div
      className={`fixed top-0 left-0 w-screen h-screen bg-center bg-no-repeat bg-cover flex items-center justify-center z-0 ${
        showUI ? "" : "hide-cursor"
      }`}
      style={{
        backgroundImage: imageURL ? `url(${imageURL})` : "none",
        backgroundSize: "cover",
        transition: "background-image 1s ease-in-out",
      }}
    >
      {/* 명언 박스 */}
      {showUI && (
        <div className="bg-black bg-opacity-30 p-6 rounded text-white text-center shadow-lg transition-opacity duration-700">
          <p className="text-3xl italic font-light">“{quote.quotes}”</p>
          <p className="text-xl mt-4"> — {quote.author} — </p>
        </div>
      )}
    </div>
  );
};

export default RandomWallpaperAndQuote;
