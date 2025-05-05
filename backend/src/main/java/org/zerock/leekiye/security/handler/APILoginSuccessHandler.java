package org.zerock.leekiye.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.leekiye.dto.MemberDTO;
import org.zerock.leekiye.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-----------------onAuthenticationSuccess");
        log.info(authentication);
        log.info("-----------------onAuthenticationSuccess");

        // JSON 데이터 만들기
        // 성공했을 때 가정(여기로 넘어왔다는 것은 성공했다는 것이기 때문)
        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        Map<String, Object> claims = memberDTO.getClaims(); // 이 claims 는 뭔지도 알아야할듯

        // 여기에 clamins 확인용 로그 추가
        log.info("claims 최종 내용: {}", claims);

        String accessToken = JWTUtil.generateToken(claims, 60*3); // 액새스 토큰 10분간 유지 (권리)
        String refreshToken = JWTUtil.generateToken(claims, 60*24); // 리프래시 토큰

        claims.put("accessToken",accessToken);
        claims.put("refreshToken",refreshToken);
        // claims를 JSON 문자열로 바꾼다

        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");
        // UTF-8 안하면 닉네임 한글 이런거 깨진다

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();

    }
}
