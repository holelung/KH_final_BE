package com.kh.saintra.meetingroom.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MeetingRoomRequestDTO {
	
	@NotNull(message = "회의실을 선택해주세요.")
    private Long roomId;

    @NotNull(message = "예약자 유형을 선택해주세요.")
    private String reserverType;

    @NotNull(message = "예약자 또는 팀을 선택해주세요.")
    private Long reserverId;

    @NotBlank(message = "예약 날짜는 필수입니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜 형식은 yyyy-MM-dd입니다.")
    private String reserveDate;

    @NotBlank(message = "시작 시간은 필수입니다.")
    private String startTime;

    @NotBlank(message = "종료 시간은 필수입니다.")
    private String endTime;

    @NotBlank(message = "예약 목적은 필수입니다.")
    @Size(max = 20, message = "목적은 20자 이하로 입력해주세요.")
    private String purpose;
	

}
