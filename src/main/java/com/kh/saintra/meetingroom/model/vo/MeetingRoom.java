package com.kh.saintra.meetingroom.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Data
@Builder
@AllArgsConstructor
@ToString
public class MeetingRoom {

	private Long id;
	private String reserverType;
	
	private Long reserverId;
	private Long reservationId;
	private Long roomId;
	private Long createdBy;
	private String reserveDate;
	private String startTime;
	private String endTime;
	private String purpose;
	


}
