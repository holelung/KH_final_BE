package com.kh.saintra.auth.model.service;

import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.AuthenticateFailException;
import com.kh.saintra.user.model.dao.UserMapper;
import com.kh.saintra.user.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
    
    private final UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userMapper.getUserByUsername(username);
        if( user == null) {
            throw new AuthenticateFailException(ResponseCode.INVALID_USERDATA, "아이디 혹은 비밀번호가 잘못되었습니다.");
        }

        return CustomUserDetails.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .email(user.getEmail())
            .role(user.getRole())
            .realname(user.getRealname())
            .jobId(user.getJobId())
            .deptId(user.getDeptId())
            .teamId(user.getTeamId())
            .isActive(user.getIsActive())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
            .build();
    }
    
    
}
