import jwtAxios from "../util/jwtUtil";
import { API_SERVER_HOST } from "./todoApi";

import axios from "axios";

const host = `${API_SERVER_HOST}/makemyday/wallpaper`;

// const host = `${API_SERVER_HOST}/makemyday/wallpapers`;

// multipart/form-data는 axios가 FormData 객체를 감지하면 자동으로 처리
// 함으로 헤더 설정 제거해도 됨

// 해당 월페이퍼의 저장글을 불러오는 것
export const getOne = async (ord) => {
  const res = await jwtAxios.get(`${host}/${ord}`); // 오타있었음

  return res.data;
};

// 스프링의 WallpaperController 참고, 이와 연동함
// 혹시 컨텐츠 타입, authorization 설정 이런거 때문에 무한 리다이렉트 오류 같은게 발생했던 건가
// export const getList = async (pageParam) => {
//   const { page, size } = pageParam; // 구조분해 할당?

//   const token = getCookie("wallpaper")?.accessToken; // 월페이퍼 불러오는데 쿠키가 필요할까?

//   const headers = {
//     // 이것도 필요할까?
//     Authorization: `Bearer ${token}`,
//     "Content-Type": "application/json",
//   };

//   const res = await jwtAxios.get(`${host}/list`, {
//     params: { page: page, size: size },
//   });

//   return res.data;
// };

// 월페이퍼 리스트 불러오기
export const getList = async ({ page, size }) => {
  const res = await jwtAxios.get(`${host}/list`, {
    params: { page, size },
  });
  return res.data;
};

// 월페이퍼 추가
export const postAdd = async (formData) => {
  const res = await jwtAxios.post(`${host}/register`, formData);
  return res.data;
};

// 스프링은 멀티파트 폼 데이터 안받는다고 해서 put 에서 post 로 변경
// 월페이퍼 수정
export const modifyOne = async (ord, formData) => {
  const res = await jwtAxios.post(`${host}/modify/${ord}`, formData);
  return res.data;
};

// 월페이퍼 삭제
export const deleteOne = async (ord) => {
  const res = await jwtAxios.delete(`${host}/${ord}`);

  return res.data;
};
