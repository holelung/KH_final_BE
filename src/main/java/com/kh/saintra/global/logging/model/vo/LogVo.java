package com.kh.saintra.global.logging.model.vo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class LogVo {
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long currentPage;
    private Long rowsPerPage;
    private String search;
}
