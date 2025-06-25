package com.kh.saintra.log.model.dto;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private Long id;
    private Long userId;
    private String actionArea;
    private String actionType;
    private String actionResult;
    private Timestamp actionTime;
    private String clientIp;
    private String referer;
}
