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
import com.kh.saintra.user.model.dto.Attendance;
import com.kh.saintra.user.model.dto.AttendanceRequest;
import com.kh.saintra.user.model.dto.ListRequest;
import com.kh.saintra.user.model.dto.UserCompanyInfoDTO;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.dto.UserPasswordDTO;
import com.kh.saintra.user.model.dto.UserProfileDTO;
import com.kh.saintra.user.model.dto.UserSearchDTO;
import com.kh.saintra.user.model.dto.UserUpdateEmailDTO;
import com.kh.saintra.user.model.service.UserService;
import com.kh.saintra.user.model.vo.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByMe(@ModelAttribute ListRequest request) {
        
        return ResponseEntity.ok(userService.getUserByMe());
    }
    

    // 사용자 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUserList(){
        
        return ResponseEntity.ok(userService.getUserList());
    }

    // 사용자 목록 조회(관리자용)
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserListByAdmin(@ModelAttribute UserSearchDTO search){
        
        return ResponseEntity.ok(userService.getUserListByAdmin(search));
    }

    // 사용자 정보 변경
    @PutMapping("/mypage")
    public ResponseEntity<ApiResponse<Void>> updateUserProfile(@RequestBody @Valid UserProfileDTO userProfile){
        System.out.println("User정보 업데이트 컨트롤러");
        return ResponseEntity.ok(userService.updateUser(userProfile));
    }

    // 사용자 정보변경 ( 관리자 )
    @PutMapping("/admin")
    public ResponseEntity<ApiResponse<Void>> updateUserByAdmin(@RequestBody UserCompanyInfoDTO request){
        log.info("관리자의 사용자 정보 변경(부서)");
        return ResponseEntity.ok(userService.updateUserByAdmin(request));
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

    //  이메일 변경
    @PatchMapping("/email")
    public ResponseEntity<ApiResponse<Void>> changeEmail(@RequestBody @Valid UserUpdateEmailDTO email){

        return ResponseEntity.ok(userService.updateEmail(email));
    }

    // 회원탈퇴
    @DeleteMapping("/mypage")
    public ResponseEntity<ApiResponse<Void>> dropUser(){

        return ResponseEntity.ok(userService.deleteUser());
    }

    // 회원 강퇴
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> dropUserByAdmin(@RequestBody Map<String, Long> id){
        return ResponseEntity.ok(userService.deleteUserByAdmin(id.get("id")));
    }

    // 근태조회(유저)
    @GetMapping("/attendance")
    public ResponseEntity<ApiResponse<List<Attendance>>> getAttendance(@ModelAttribute AttendanceRequest request){
    
        return ResponseEntity.ok(userService.getAttendance(request));
    }

    // 근태조회(관리자)
    @GetMapping("/admin/attendance")
    public ResponseEntity<ApiResponse<List<Attendance>>> getAttendanceByAdmin(@RequestBody AttendanceRequest request){

        return ResponseEntity.ok(userService.getAttendanceByAdmin(request));
    }

    // 출근
    @PostMapping("/attendance")
    public ResponseEntity<ApiResponse<Void>> checkIn() {
         
        return ResponseEntity.ok(userService.checkIn());
    }

    // 퇴근 
    @DeleteMapping("/attendance")
    public ResponseEntity<ApiResponse<Void>> checkOut() {

        return ResponseEntity.ok(userService.checkOut());
    }

    @GetMapping("/company")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllCompany() {
        log.info("부서, 팀, 직급 리스트 조회");
        return ResponseEntity.ok(userService.getCompanyInfo());
    }
    
}
