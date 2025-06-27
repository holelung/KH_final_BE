package com.kh.saintra.global.logging.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LogDTO {
    
    private Long id;
    private Long userId;
    private String actionArea;
    private String actionType;
    private String actionResult;
    private LocalDateTime actionTime;
    private String clientIp;
    private String referer;
}
