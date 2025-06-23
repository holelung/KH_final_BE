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
    private String isActive;
}
