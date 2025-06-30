package com.kh.saintra.global.logging.model.vo;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class Log {
    
    private Long id;
    private Long userId;
    private String actionArea;
    private String actionType;
    private String actionResult;
    private LocalDateTime actionTime;
    private String clientIp;
    private String referer;
}
