package com.kh.saintra.user.controller;


import java.util.List;
import java.util.Map;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kh.saintra.auth.model.dto.ChangePasswordDTO;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.dto.UserPasswordDTO;
import com.kh.saintra.user.model.dto.UserProfileDTO;
import com.kh.saintra.user.model.dto.UserSearchDTO;
import com.kh.saintra.user.model.dto.UserUpdateEmailDTO;
import com.kh.saintra.user.model.service.UserService;
import com.kh.saintra.user.model.vo.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Void>> join(@RequestBody @Valid UserDTO user) {
        
        userService.join(user);

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.INSERT_SUCCESS, "회원가입 성공"));
    }

    // 마이페이지 정보 조회
    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(){

        return ResponseEntity.ok(userService.getUser());

    }

    // 사용자 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUserList(@ModelAttribute UserSearchDTO search){
        
        return ResponseEntity.ok(userService.getUserList(search));
    }

    // 사용자 정보 변경
    @PutMapping("/mypage")
    public ResponseEntity<ApiResponse<Void>> updateUserProfile(@RequestBody @Valid UserProfileDTO userProfile){
        return ResponseEntity.ok(userService.updateUser(userProfile));
    }

    // 사용자 프로필 사진 변경
    @PatchMapping("/mypage")
    public ResponseEntity<ApiResponse<Void>> updateProfileImage(@RequestParam(name = "file") MultipartFile file){

        return null;
    }

    // 비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody @Valid UserPasswordDTO passwords){
        
        return ResponseEntity.ok(userService.changePassword(passwords));

    }

    @PatchMapping("/email")
    public ResponseEntity<ApiResponse<Void>> changeEmail(@RequestBody @Valid UserUpdateEmailDTO email){

        return ResponseEntity.ok(userService.updateEmail(email));
    }

    @DeleteMapping("/mypage")
    public ResponseEntity<ApiResponse<Void>> dropUser(@RequestBody Map<String, String> password){

        return ResponseEntity.ok(userService.deleteUser(password.get("password")));
    }

}
