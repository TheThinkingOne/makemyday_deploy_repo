// createAsyncthunk 로 로그인 유지

import axios from "axios";
import { API_SERVER_HOST } from "./todoApi";

//
const host = `${API_SERVER_HOST}/makemyday/member`;

// 스프링은 application/json 으로 로그인 요청 받고 있었음 그래서 여기도 그렇게 해야함
// export const loginPost = async (loginParam) => {
//   const header = { headers: { "Content-Type": "application/json" } };

//   const body = {
//     username: loginParam.userID,
//     password: loginParam.password,
//   };

//   const res = await axios.post(`${host}/login`, body, header);

//   return res.data;
// };

export const loginPost = async (loginParam) => {
  const header = { headers: { "Content-Type": "application/json" } };
  const res = await axios.post(
    `${host}/login`,
    JSON.stringify(loginParam),
    header
  );
  return res.data;
};

export const checkDuplicateID = async (userID) => {
  const res = await axios.get(`${host}/check`, {
    params: { userID }, // get 방식이니 param 사용해야 함
  });

  return res.data;
};

export const modifyMember = async (member) => {
  // 어쩔때 params 보내고 어쩔때 JSON 헤더 보내는걸까
  const res = await axios.put(`${host}/modify`, member);

  return res.data;
};

export const registerMember = async (member) => {
  const header = { headers: { "Content-Type": "application/json" } };

  const res = await axios.post(
    `${host}/register`,
    JSON.stringify(member),
    header
  );

  return res.data;
};
