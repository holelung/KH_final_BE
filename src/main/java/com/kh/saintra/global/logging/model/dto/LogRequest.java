package com.kh.saintra.global.logging.model.dto;


import java.time.LocalDate;
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
public class LogRequest {

    
    private LocalDate startDate;
    private LocalDate endDate;
    private Long currentPage;
    private Long rowsPerPage;
    private String search;
}
