package com.kh.saintra.global.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.AuthenticateFailException;
import com.kh.saintra.global.error.exceptions.AuthenticateTimeOutException;
import com.kh.saintra.global.error.exceptions.DataAccessException;
import com.kh.saintra.global.error.exceptions.DuplicateDataException;
import com.kh.saintra.global.error.exceptions.FileNotAllowedException;
import com.kh.saintra.global.error.exceptions.FileStreamException;
import com.kh.saintra.global.error.exceptions.InvalidAccessException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;
import com.kh.saintra.global.error.exceptions.UnauthorizedAccessException;
import com.kh.saintra.global.response.ApiResponse;

import jakarta.validation.ConstraintViolationException;
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

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataAccessException(DataAccessException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticateTimeOutException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticateTimeOutException(AuthenticateTimeOutException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticateFailException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticateFailException(AuthenticateFailException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidAccessException(InvalidAccessException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateDataException(DuplicateDataException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidValueException(InvalidValueException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStreamException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileStreamException(FileStreamException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotAllowedException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileNotAllowedException(FileNotAllowedException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        return makeResponseEntity(ResponseCode.INVALID_VALUE, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 예시용. 
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        return makeResponseEntity(ResponseCode.SERVER_ERROR, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


