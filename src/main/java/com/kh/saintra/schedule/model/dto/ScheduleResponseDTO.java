package com.kh.saintra.schedule.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {
	
	private Long id; 

    private String title;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    private String colorCode;

    private String reserverType;

    private Long reserverId;

    private String reserverName;
    
    private String isActive;
    
    private Long createdBy;
	

}
