// ajax 통신하는 함수 작성
// api jsx 파일은 API 호출과 관련된 함수 및 경로 설정을 관리하는 역할
// GET PUT DELETE 같은 CRUD에 필요한 함수 작성
import jwtAxios from "../util/jwtUtil"; // axios 에서 jwtAxios 로 변경

// PageParam에서 page(페이지 번호)와 size(페이지 크기)를 추출

// async 의 모든 리턴값은 비동기이다.(Promise 객체)
// await 를 사용하면 Promise가 해결된 값(실제 데이터)을 반환함

// 경로 설정
// api 서버 작업
// 비동기 통신이란 서버로 데이터를 요청한 후,
// 응답이 올 때까지 기다리지 않고 다른 작업을 진행할 수 있는 방식입니다.

export const API_SERVER_HOST = "http://localhost:8080"; // api 서버의 기본 URL 설정
// 스프링 어플리케이션 실행하니까 게시글 조회 페이지에 tno 값이 제대로 뜬다.

const prefix = `${API_SERVER_HOST}/makemyday/todo`; // API 요청의 기본 경로 설정
// 위에껄 /makemyday/todo 로 바꿔야 하나
// prefix : 모든 API 요청은 /api/todo 경로를 기준으로 진행

// 비동기 통신

// 단건 조회
export const getOne = async (tno) => {
  const res = await jwtAxios.get(`${prefix}/${tno}`);
  return res.data;
};

// 리스트 조회 (페이징 포함)
export const getList = async ({ page, size }) => {
  const res = await jwtAxios.get(`${prefix}/list`, { params: { page, size } });
  return res.data;
};

// 등록
export const postAdd = async (todo) => {
  const res = await jwtAxios.post(`${prefix}/`, todo);
  return res.data;
};

// 삭제
export const deleteOne = async (tno) => {
  const res = await jwtAxios.delete(`${prefix}/${tno}`);
  return res.data;
};

// 수정
export const putOne = async (todo) => {
  const res = await jwtAxios.put(`${prefix}/${todo.tno}`, todo);
  return res.data;
};
