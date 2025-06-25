package com.kh.saintra.global.util.token.model.vo;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {
    private Long userId;
    private String token;
    private LocalDateTime expiration; 
}
