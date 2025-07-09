package com.kh.saintra.schedule.model.dto;

import java.time.LocalDate;
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
public class ScheduleRequestDTO {

	@NotBlank(message = "제목은 필수입니다.")
    @Size(max = 32, message = "제목은 32자 이하로 입력해주세요.")
    @Pattern(regexp = Regexp.TEXT, message = "제목은 한글, 영문, 숫자, 공백, 특수문자만 입력 가능합니다.")
    private String title;
	
	@Size(min = 1, max = 100, message = "내용은 1자 이상 100자 이하로 입력해주세요.")
    @Pattern(regexp = Regexp.TEXT, message = "내용은 한글, 영문, 숫자, 공백, 특수문자만 입력 가능합니다.")
    private String content;
    
    private LocalDate startDate; 
 
    private LocalDate endDate;   
	
    @Pattern(regexp = Regexp.COLOR, message = "색상 코드는 #으로 시작하는 6자리 16진수여야 합니다.")
    private String colorCode;

    @NotBlank(message = "예약자 유형을 선택해주세요.")
    private String reserverType;
	
    private Long reserverId;
	
	private Long scheduleId;
	
	private Long userId; 
	
	private Long teamId;

}
