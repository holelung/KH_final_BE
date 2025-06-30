package com.kh.saintra.schedule.model.dto;

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
	
	private Long scheduleId;

    private String title;

    private String content;

    private String startDate;

    private String endDate;

    private String colorCode;

    private String reserverType;

    private Long reserverId;

    private String reserverName;
    
    private String isActive;
	

}
