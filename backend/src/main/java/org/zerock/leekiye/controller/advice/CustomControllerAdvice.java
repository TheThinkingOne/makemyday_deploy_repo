package org.zerock.leekiye.controller.advice;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.leekiye.util.CustomJWTException;

/**
 * CustomControllerAdvice
 */

// 페이지 오류 관련 처리
@RestControllerAdvice
// @RestControllerAdvice : 모든 @RestController에 대해 전역적으로 예외를 처리할 수 있도록 도와주는 어노테이션
public class CustomControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> notExist(NoSuchElementException e) {
        // NoSuchElementException : 데이터베이스나 컬렉션에서 원하는 요소를 찾을 수 없을 때 발생
        String msg = e.getMessage();

        // NOT_FOUND => HTTP 상태 코드를 404 NOT FOUND 로 설정
        // 예외 메시지를 JSON 형식으로 응답
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", msg));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(MethodArgumentNotValidException e) {
        // MethodArgumentNotValidException
        // => 컨트롤러 메서드에 @Valid를 사용한 객체가 유효하지 않은 경우 이 예외가 발생

        String msg = e.getMessage();

        // not acceptable, forbidden,
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", msg));
    }

    @ExceptionHandler(CustomJWTException.class)
    protected ResponseEntity<?> handleJWTException(CustomJWTException e) {
        // jwt exception 이 발생하면 에러메세지 발생시키기

        String msg = e.getMessage();

        return ResponseEntity.ok().body(Map.of("error", msg));
    }

}


