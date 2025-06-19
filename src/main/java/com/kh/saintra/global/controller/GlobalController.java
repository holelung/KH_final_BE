package com.kh.saintra.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.AuthenticateTimeOutException;
import com.kh.saintra.global.error.exceptions.DataAccessException;
import com.kh.saintra.global.response.ApiResponse;


@RestController
@RequestMapping("/api")
public class GlobalController {


    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getRoot() {
        throw new AuthenticateTimeOutException(ResponseCode.SERVER_ERROR, "히히 에러당!");

        // return ResponseEntity.ok(ApiResponse.success(ResponseCode.SUCCESS, "루트 요청 성공입니다."));
    }

}
