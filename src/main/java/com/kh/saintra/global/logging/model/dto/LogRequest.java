package com.kh.saintra.global.logging.model.dto;


import java.time.LocalDateTime;
import com.kh.saintra.global.annotation.StartBeforeEnd;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@StartBeforeEnd(start = "startDate", end = "endDate")
public class LogRequest {

    
    private String startDate;
    private String endDate;
    private Long currentPage;
    private Long rowsPerPage;
    private String search;
}
