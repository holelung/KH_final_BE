package com.kh.saintra.auth.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginInfo {
    private String id;
    private String username;
    private String realname;
    private String email;
    private final String role;
    private final Long jobId;
    private final Long deptId;
    private final Long teamId;
    private String isActive;
}
