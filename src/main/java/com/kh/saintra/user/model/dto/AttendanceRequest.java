package com.kh.saintra.user.model.dto;

import java.time.LocalDate;
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
public class AttendanceRequest {
    
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate; 
    
}
