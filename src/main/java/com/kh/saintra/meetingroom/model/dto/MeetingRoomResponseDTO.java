package com.kh.saintra.meetingroom.model.dto;

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
public class MeetingRoomResponseDTO {
	
    private Long reservationId;
    
    private Long roomId;    
    
    private String roomName;
    
    private String roomLocation;
    
    private int capacity;   
    
    private String reserveDate;
    
    private String startTime;
    
    private String endTime;
    
    private String purpose; 

    private String reserverType;

    private Long reserverId;

    private String reserverName;

    private String isActive;
    
    private Long createdBy;
    

}
