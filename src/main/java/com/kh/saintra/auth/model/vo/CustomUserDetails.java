package com.kh.saintra.auth.model.vo;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class CustomUserDetails implements UserDetails{
    
    private final Long id;
    private final String username;
    private final String password;
    private final String realname;
    private final String email;
    private final String role;
    private final Long jobId;
    private final Long deptId;
    private final Long teamId;
    private final String isActive;
    private final Collection<? extends GrantedAuthority> authorities;

}
