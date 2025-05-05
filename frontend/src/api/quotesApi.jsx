// import jwtAxios from "../util/jwtUtil";
// import { API_SERVER_HOST } from "./todoApi";

// export const prefix = `${API_SERVER_HOST}/makemyday/quotes`;

// // 여긴 로그인 한 사용자 아무나 적을 수 있음
// // 딱히 작성자가 누군지 안 가려도 됨
// const host = `${API_SERVER_HOST}/makemyday/quotes`;

// // 해당 인용구 게시글 불러오기
// export const getOne = async (qno) => {
//   const res = await jwtAxios.get(`${prefix}/${qno}`);

//   return res.data;
// };

// // 인용구 리스트 불러오기
// export const getList = async (PageParam) => {
//   const { page, size } = PageParam;

//   const res = await jwtAxios.get(`${prefix}/list`, { params: { page, size } });
//   // PageParam에서 page(페이지 번호)와 size(페이지 크기)를 추출

//   // async 의 모든 리턴값은 비동기이다.(Promise 객체)
//   // await 를 사용하면 Promise가 해결된 값(실제 데이터)을 반환함
//   return res.data;

//   // axios와 async/await 장점
//   // 1. 간결한 비동기 처리, 유지보수성, 확장성

//   // 유즈 스테이트?
// };

// // 이건 로그인한 사용자 누구나 추가 가능
// export const postAdd = async (quotesObj) => {
//   // JSON.stringify(obj) => 어쩌구 이런거 할필요 없음 axios 사용하면
//   const res = await jwtAxios.post(`${prefix}/`, quotesObj);

//   return res.data;
// };

// // 이건 관리자만 삭제 가능하게 구현
// export const deleteOne = async (qno) => {
//   // 게시글 삭제
//   const res = await jwtAxios.delete(`${prefix}/${qno}`);

//   return res.data;
// };

import axios from "axios";
import jwtAxios from "../util/jwtUtil";
import { API_SERVER_HOST } from "./todoApi";

const host = `${API_SERVER_HOST}/makemyday/quotes`;

// 누구나 등록 가능
export const postAdd = async (quotesObj) => {
  const res = await axios.post(`${host}/register`, quotesObj);
  return res.data;
};

// 관리자만 조회 가능
export const getList = async ({ page, size }) => {
  const res = await jwtAxios.get(`${host}/list`, { params: { page, size } });
  return res.data;
};

// 관리자만 해당 인용구 조회 가능
export const getOne = async (qno) => {
  const res = await jwtAxios.get(`${host}/${qno}`);
  return res.data;
};

// 관리자만 삭제 가능
export const deleteOne = async (qno) => {
  const res = await jwtAxios.delete(`${host}/${qno}`);
  return res.data;
};
