package com.kh.saintra.auth.model.vo;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class ApproveUser {
    private Long id;
    private String username;
    private String password;
    private String realname;
    private String email;
    private String address1;
    private String address2;
    private String phone;
    private String ssn;
    private String isActive;
    private LocalDateTime issueDate;
}
