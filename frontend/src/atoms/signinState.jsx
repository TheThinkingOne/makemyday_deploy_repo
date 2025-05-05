import { atom } from "recoil";
import { getCookie } from "../util/cookieUtil";

const initState = {
  //
  userID: "",
  userName: "",
  isSocial: false,
  accessToken: "",
  refreshToken: "",
  roleNames: [], // 이게 빠져있어서 권한 못 불러왔었음
};

const loadMemberCookie = () => {
  const memberInfo = getCookie("member");

  // 한글닉네임 관련 처리 ]
  if (memberInfo && memberInfo.nickname) {
    memberInfo.nickname = decodeURIComponent(memberInfo.nickname);
  }

  // roleNames 없으면 빈 배열로 설정하기
  if (memberInfo && !Array.isArray(memberInfo.roleNames)) {
    memberInfo.roleNames = [];
  }

  return memberInfo;
};

export const signinState = atom({
  key: "signinState",
  default: loadMemberCookie() || initState, // 쿠기 있으면 왼쪽으로 로그인, 없으면 빈창
});
