package com.kh.saintra.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.saintra.auth.model.dto.LoginFormDTO;
import com.kh.saintra.auth.model.service.AuthService;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/tokens")
    public ResponseEntity<ApiResponse<Map<String,Object>>> login(@RequestBody @Valid LoginFormDTO login) {
        
        return ResponseEntity.ok(authService.login(login));
    }
    
    @GetMapping("/approve")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getApproveList(){
        return null;
    }

    @PostMapping("/approve")
    public ResponseEntity<ApiResponse<Void>> approveJoin(@RequestBody Map<String, String> approve) {

        return ResponseEntity.ok(authService.approveJoin(Long.parseLong(approve.get("id"))));
    }
    
}
