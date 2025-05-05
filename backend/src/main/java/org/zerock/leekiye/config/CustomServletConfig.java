package org.zerock.leekiye.config;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.leekiye.controller.formatter.LocalDateFormatter;
import org.zerock.leekiye.service.TodoService;

@Configuration // 스프링의 설정 클래스임을 나타냄
@Log4j2
@RequiredArgsConstructor
public class CustomServletConfig implements WebMvcConfigurer {

    private final TodoService todoService;

    @Override
    public void addFormatters(FormatterRegistry registry) {

        log.info("addFormatters");

        registry.addFormatter(new LocalDateFormatter());
    }
}
