package com.kh.saintra.auth.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kh.saintra.auth.model.dao.AuthMapper;
import com.kh.saintra.auth.model.dto.ApproveRequest;
import com.kh.saintra.auth.model.dto.ChangePasswordDTO;
import com.kh.saintra.auth.model.dto.FindPasswordDTO;
import com.kh.saintra.auth.model.dto.LoginFormDTO;
import com.kh.saintra.auth.model.vo.ApproveUser;
import com.kh.saintra.auth.model.vo.ChangePassword;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.auth.model.vo.LoginInfo;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.AuthenticateFailException;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.error.exceptions.InvalidAccessException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.global.util.token.model.service.TokenService;
import com.kh.saintra.mail.model.service.MailService;
import com.kh.saintra.user.model.dao.UserMapper;
import com.kh.saintra.user.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<Map<String, Object>> login(LoginFormDTO user) {
        

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new AuthenticateFailException(ResponseCode.AUTH_FAIL, "비밀번호 혹은 아이디가 틀렸습니다.");
        }
        CustomUserDetails loginUser = (CustomUserDetails)authentication.getPrincipal();

        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("tokens", tokenService.generateToken(loginUser.getUsername(), loginUser.getId()));
        LoginInfo loginInfo = LoginInfo.builder()
                .id(String.valueOf(loginUser.getId()))
                .username(loginUser.getUsername())
                .realname(loginUser.getRealname())
                .email(loginUser.getEmail())
                .build();

        loginResponse.put("loginInfo", loginInfo);

        return ApiResponse.success(ResponseCode.LOGIN_SUCCESS, loginResponse, "로그인 성공");
    }

    @Override
    public ApiResponse<Void> logout() {
        
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

    @Override
    public ApiResponse<Map<String, Object>> getApproveList(ApproveRequest request) {
        Map<String, Object> result = new HashMap<>();
        request.setCurrentPage(request.getCurrentPage() * request.getRowsPerPage());

        try {
            result.put("list",authMapper.getApproveList(request));
            result.put("total", authMapper.getApproveListTotalCount(request));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "가입 요청목록 조회 실패");
        }

        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "가입요청 목록 조회 성공");
    }

    @Override
    @Transactional
    public ApiResponse<Void> approveJoin(Long userId) {
        
        // 인사팀 관리자인지 확인
        // if(getUserDetails().getDeptId() != "1"){
        //     throw new InvalidAccessException(ResponseCode.INVALID_ACCESS, "접근 권한이 없습니다!");
        // }

        // 회원가입 승인
        try {
            authMapper.approveJoin(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "회원가입 승인 실패");
        }

        // 가입 요청 삭제
        try {
            authMapper.deleteJoin(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "가입요청 테이블 행 삭제 실패");
        }

        mailService.sendCelebrateMail(userMapper.getUserByUserId(userId).getEmail());

        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "회원가입 승인 성공");
    }


    @Override
    @Transactional
    public ApiResponse<Void> findPassword(String username) {
        
        UserDTO user = null;

        try {
           user = userMapper.getUserByUsername(username);
           mailService.sendPasswordFindEmail(user.getId(), user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "존재하지 않는 회원입니다.");
        }


        return ApiResponse.success(ResponseCode.MAIL_SEND, "비밀번호 찾기 메일이 전송되었습니다.");
    }


    @Override
    public ApiResponse<Void> changePasswordByKey(ChangePasswordDTO changePassword) {
        
        Long id = authMapper.confirmAccessKey(changePassword.getAccessKey());

        if(id == null) {
            throw new InvalidAccessException(ResponseCode.INVALID_TOKEN, "존재하지 않는 토큰입니다.");
        }

        ChangePassword request 
            = ChangePassword.builder()
                .userId(id)
                .password(passwordEncoder.encode(changePassword.getPassword()))
                .build();
        
        try {
            authMapper.changePassword(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "비밀번호 변경 실패");
        }

        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "비밀번호 변경 성공!");
        
    }



    @Override
    public CustomUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        return user;
    }


    @Override
    public Long checkPassword(String password) {
        CustomUserDetails user = getUserDetails();

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidValueException(ResponseCode.INVALID_USERDATA, "비밀번호가 틀립니다.");
        }

        return user.getId();
    }
    
    
}
