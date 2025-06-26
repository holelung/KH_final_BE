package com.kh.saintra.user.model.vo;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class UpdatePassword {
    
    private Long id;
    private String password;
}
