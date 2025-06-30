package com.kh.saintra.schedule.model.dto;

import com.kh.saintra.global.annotation.StartBeforeEnd;
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
@StartBeforeEnd(start = "startDate", end = "endDate", 
				message = "종료일은 시작일과 같거나 이후여야 합니다.")
public class ScheduleRequestDTO {

	@NotBlank(message = "제목은 필수입니다.")
    @Size(max = 32, message = "제목은 32자 이하로 입력해주세요.")
    @Pattern(regexp = Regexp.TEXT, message = "제목은 한글, 영문, 숫자, 공백, 특수문자만 입력 가능합니다.")
    private String title;
	
	@Size(min = 1, max = 100, message = "내용은 1자 이상 100자 이하로 입력해주세요.")
    @Pattern(regexp = Regexp.TEXT, message = "내용은 한글, 영문, 숫자, 공백, 특수문자만 입력 가능합니다.")
    private String content;
    
	@NotBlank(message = "시작일은 필수입니다.")
    @Pattern(regexp = Regexp.DATE, message = "시작일 형식은 yyyy-MM-dd 형식이어야 합니다.")
    private String startDate; 
    
	@Pattern(regexp = Regexp.DATE, message = "종료일 형식은 yyyy-MM-dd 형식이어야 합니다.")
    private String endDate;   
	
    @Pattern(regexp = Regexp.COLOR, message = "색상 코드는 #으로 시작하는 6자리 16진수여야 합니다.")
    private String colorCode;

    @NotBlank(message = "예약자 유형을 선택해주세요.")
    private String reserverType;
	
	@NotNull(message = "예약자 ID는 필수입니다.")
    private Long reserverId;
	
	private Long scheduleId;
}
