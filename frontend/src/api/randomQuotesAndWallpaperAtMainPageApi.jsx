import jwtAxios from "../util/jwtUtil";

export const getRandomMainData = async () => {
  const res = await jwtAxios.get("/makemyday/main");
  return res.data;
};
