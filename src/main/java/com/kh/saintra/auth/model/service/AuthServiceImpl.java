package com.kh.saintra.auth.model.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.kh.saintra.auth.model.dao.AuthMapper;
import com.kh.saintra.auth.model.dto.ChangePasswordDTO;
import com.kh.saintra.auth.model.dto.FindPasswordDTO;
import com.kh.saintra.auth.model.dto.LoginFormDTO;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.auth.model.vo.LoginInfo;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.AuthenticateFailException;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.global.util.token.model.service.TokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;
    private final TokenService tokenService;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

    @Override
    public ApiResponse<Object> getApproveList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getApproveList'");
    }

    @Override
    public ApiResponse<Void> approveJoin(Long username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'approveJoin'");
    }

    @Override
    public ApiResponse<Void> findPassword(FindPasswordDTO findPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPassword'");
    }

    @Override
    public ApiResponse<Void> changePasswordByKey(ChangePasswordDTO changePassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePasswordByKey'");
    }

    @Override
    public ApiResponse<Void> changePassword(ChangePasswordDTO changePassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }
    
}
