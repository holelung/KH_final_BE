package com.kh.saintra.user.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> join(@RequestBody @Valid UserDTO user) {
        
        userService.join(user);

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.GET_SUCCESS, "데이터를 보내용~"));
    }
}
