package com.kh.saintra.user.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.saintra.auth.model.dto.EmailDTO;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dto.AttendanceDTO;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.dto.UserProfileDTO;
import com.kh.saintra.user.model.dto.UserSearchDTO;
import com.kh.saintra.user.model.dto.UserUpdateDTO;

public interface UserService {
    
    // 회원가입
    ApiResponse<Void> join(UserDTO user);

    // 회원 목록 조회
    ApiResponse<Object> getUserList(UserSearchDTO userSearch);

    // 회원 정보 수정(관리자) -> 부서/팀 변경
    ApiResponse<Void> updateUserByAdmin(UserUpdateDTO userUpdate);
    // 회원 정보 수정(유저)
    ApiResponse<Void> updateUser(UserProfileDTO userProfile);
    // 회원 프로필 사진 변경
    ApiResponse<Void> updateUserProfileImage(MultipartFile file);
    // 이메일 변경
    ApiResponse<Void> updateEmail(EmailDTO email);

    // 회원 탈퇴
    ApiResponse<Void> deleteUser(String password);
    ApiResponse<Void> deleteUserByAdmin(String username);
    
    // 근태 조회
    ApiResponse<Object> getAttendance(AttendanceDTO attendance);

    // 출근
    ApiResponse<Void> checkIn();
    // 퇴근
    ApiResponse<Void> checkOut();
}
