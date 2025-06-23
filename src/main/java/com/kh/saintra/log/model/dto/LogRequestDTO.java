package com.kh.saintra.log.model.dto;

import java.sql.Date;

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
public class LogRequestDTO {
    
    private String sortOrder;
    private String search;
    
    private String actionType;
    private String actionArea;

    private Date startDate;
    private Date endDate;
    private Long rowsPerPage;
    private Long currentPage;
}
