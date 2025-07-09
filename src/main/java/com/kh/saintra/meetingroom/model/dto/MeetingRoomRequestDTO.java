package com.kh.saintra.meetingroom.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.kh.saintra.global.util.Regexp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class MeetingRoomRequestDTO {
	
	@NotNull(message = "회의실을 선택해주세요.")
    private Long roomId;

    @NotNull(message = "예약자 유형을 선택해주세요.")
    private String reserverType;

    private Long reserverId;

    private LocalDate reserveDate;

    
    private LocalTime startTime;

    
    private LocalTime endTime;

    @NotBlank(message = "예약 목적은 필수입니다.")
    @Size(max = 20, message = "목적은 20자 이하로 입력해주세요.")
    private String purpose;
    
    private Long reservationId;
	
	private Long userId; 
	
	private Long teamId;

}
