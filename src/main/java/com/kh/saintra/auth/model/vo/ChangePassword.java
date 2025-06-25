package com.kh.saintra.auth.model.vo;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class ChangePassword {
    
    private Long userId;
    private String password;
}
