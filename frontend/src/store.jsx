// src/store.jsx
import { configureStore } from "@reduxjs/toolkit";
import loginSlice from "./slices/loginSlice.jsx";

// 🚀 Redux Store 설정
export default configureStore({
  reducer: {
    loginSlice: loginSlice, // loginSlice 등록
  },
  devTools: process.env.NODE_ENV !== "production", // Redux DevTools 연결
});
// 여기는 const 함수명 ~ 으로 해야하나
// 아니면 export default 로 해야한

// export default configureStore({
//   reducer: {
//     "loginSlice" : loginSlice
//   }
// })

// 여기 다시 확인
