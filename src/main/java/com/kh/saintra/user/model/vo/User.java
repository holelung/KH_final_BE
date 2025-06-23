package com.kh.saintra.user.model.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Getter
@Value
@Builder
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
    private String realname;
    private String email;
    private String address1;
    private String address2;
    private String phone;
    private String ssn;
    
    private String jobId;
    private String deptId;
    private String teamId;
    private String role;
    private LocalDateTime enrollDate;
    private String isActive;    
}
