package com.kh.saintra.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private <T> ResponseEntity<ApiResponse<T>> makeResponseEntity(
            ResponseCode code,
            String message,
            HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(ApiResponse.error(code, message));
    }

    // 예시용. 
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        return makeResponseEntity(ResponseCode.SERVER_ERROR, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
