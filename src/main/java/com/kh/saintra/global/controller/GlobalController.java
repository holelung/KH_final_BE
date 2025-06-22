package com.kh.saintra.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.AuthenticateTimeOutException;
import com.kh.saintra.global.response.ApiResponse;


@RestController
@RequestMapping("/api")
public class GlobalController {
    
    @GetMapping
    public ResponseEntity<ApiResponse<Void>> getTest() {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.GET_SUCCESS, "루트 요청 성공입니다."));
    }


    @GetMapping("/error")
    public ResponseEntity<ApiResponse<Void>> getError() {
        throw new AuthenticateTimeOutException(ResponseCode.SERVER_ERROR, "히히 에러당!13123");
    }
}
