package com.kh.saintra.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.saintra.auth.model.dto.ApproveRequest;
import com.kh.saintra.auth.model.dto.ChangePasswordDTO;
import com.kh.saintra.auth.model.dto.FindPasswordDTO;
import com.kh.saintra.auth.model.dto.LoginFormDTO;
import com.kh.saintra.auth.model.service.AuthService;
import com.kh.saintra.auth.model.vo.ApproveUser;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.vo.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> getApproveList(@ModelAttribute ApproveRequest request){
        
        return ResponseEntity.ok(authService.getApproveList(request));
    }

    @PostMapping("/approve")
    public ResponseEntity<ApiResponse<Void>> approveJoin(@RequestBody Map<String, String> approve) {

        return ResponseEntity.ok(authService.approveJoin(Long.parseLong(approve.get("id"))));
    }
    
    @PostMapping("/password")
    public ResponseEntity<ApiResponse<Void>> findPassword(@RequestBody Map<String, String> username) {
        
        return ResponseEntity.ok(authService.findPassword(username.get("username")));
    }
    
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody @Valid ChangePasswordDTO password){

        return ResponseEntity.ok(authService.changePasswordByKey(password));
    }
}
