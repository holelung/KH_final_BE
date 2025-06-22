package com.kh.saintra.auth.model.service;

import java.util.Map;

import com.kh.saintra.auth.model.dto.ChangePasswordDTO;
import com.kh.saintra.auth.model.dto.FindPasswordDTO;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dto.UserDTO;

public interface AuthService {

    // 로그인
    ApiResponse<Map<String,Object>> login(UserDTO user);
    // 로그아웃
    ApiResponse<Void> logout();

    // 회원가입 요청 목록 조회
    ApiResponse<Object> getApproveList();
    // 회원가입 승인
    ApiResponse<Void> approveJoin(Long username);

    // 비밀번호 찾기
    ApiResponse<Void> findPassword(FindPasswordDTO findPassword);
    // 비밀번호 변경(비로그인)
    ApiResponse<Void> changePasswordByKey(ChangePasswordDTO changePassword);
    // 비밀번호 변경
    ApiResponse<Void> changePassword(ChangePasswordDTO changePassword);

}
