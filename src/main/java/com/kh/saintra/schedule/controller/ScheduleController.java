package com.kh.saintra.schedule.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.auth.model.service.AuthService;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.schedule.model.dto.ScheduleRequestDTO;
import com.kh.saintra.schedule.model.dto.ScheduleResponseDTO;
import com.kh.saintra.schedule.model.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	private final AuthService authService;
	
    /**
     * 일정 조회 (기간 내 활성화된 일정만)
     * @param startDate 시작일 (yyyy-MM-dd)
     * @param endDate 종료일 (yyyy-MM-dd)
     * @return 일정 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleResponseDTO>>> getSchedules(
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate) {

        List<ScheduleResponseDTO> scheduleList = scheduleService.getSchedules(startDate, endDate);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.GET_SUCCESS, scheduleList, "일정 조회에 성공했습니다.")
        );
    }
    
    /**
     * 일정 등록 
     * @param dto 일정 등록에 필요한 데이터 (제목, 날짜, 색상 등)
     * @return 생성된 일정 ID를 포함한 ApiResponse
     */
    @PostMapping("/write")
    public ResponseEntity<ApiResponse<Long>> createSchedule(
            @Validated @RequestBody ScheduleRequestDTO dto) {

    	CustomUserDetails userDetails = authService.getUserDetails();

        Long createdBy = userDetails.getId();
        Long scheduleId = scheduleService.createSchedule(dto, createdBy);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.INSERT_SUCCESS, scheduleId, "일정이 등록되었습니다."));
    }
    
	
	
}
