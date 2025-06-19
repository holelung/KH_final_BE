package com.kh.saintra.user.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;


@RestController
@RequestMapping("/api/members")
public class UserController {
    
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTest() {
        

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.SUCCESS, "데이터를 보내용~"));
    }
}
