package com.kh.saintra.user.model.dto;

import java.sql.Date;
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
public class AttendanceRequest {
    
    private Long id;
    private Date startDate;
    private Date endDate; 
    
}
