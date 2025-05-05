package org.zerock.leekiye.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException, ServletException {
        log.info("Login failed--------" + exception);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(Map.of("error", "Login Error!!"));

        resp.setContentType("application/json"); // 상태코드 따로 안두면 200임
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(jsonStr);
        printWriter.close();
    }
}
