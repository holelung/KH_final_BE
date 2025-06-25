package com.kh.saintra.global.util.token.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Tokens {
    
    private String accessToken;
    private String refreshToken;
    
}
