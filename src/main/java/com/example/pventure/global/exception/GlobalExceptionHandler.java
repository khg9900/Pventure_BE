package com.example.pventure.global.exception;

import com.example.pventure.global.response.ApiResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 존재하지 않는 요청에 대한 예외
    @ExceptionHandler({ResponseStatusException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleNoPageFoundException(Exception e) {
        log.warn("NoHandlerFoundException or HttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ApiResponseHelper.fail(new ApiException(ErrorCode.NOT_FOUND_ENDPOINT));
    }


    // 커스텀 예외
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleCustomException(ApiException e) {
        log.error("ApiException caught: {} ({})", e.getMessage(), e.getErrorCode().name());
        return ApiResponseHelper.fail(e);
    }

    // 기본 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception caught: {}", e.getMessage(), e);
        return ApiResponseHelper.fail(new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}