package com.kh.saintra.schedule.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import jakarta.validation.Valid;
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
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate) {

        List<ScheduleResponseDTO> scheduleList = scheduleService.getSchedules(startDate, endDate);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.GET_SUCCESS, scheduleList, "일정 조회에 성공했습니다.")
        );
    }
    
    /**
     * 일정 등록 
     * @param dto 일정 등록에 필요한 데이터
     * @return 생성된 일정 ID를 포함한 ApiResponse
     */
    @PostMapping("/write")
    public ResponseEntity<ApiResponse<Long>> createSchedule(
            @Valid @RequestBody ScheduleRequestDTO dto) {

    	CustomUserDetails userDetails = authService.getUserDetails();

        Long createdBy = userDetails.getId();
        Long scheduleId = scheduleService.createSchedule(dto, createdBy);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.INSERT_SUCCESS, scheduleId, "일정이 등록되었습니다."));
    }
    
    /**
     * 일정 수정 
     * @param scheduleId 수정할 일정 ID
     * @param dto 수정할 일정 정보
     * @return 수정된 일정 ID와 함께 성공 응답 반환
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<Long>> updateSchedule(
    		@PathVariable(name = "scheduleId") Long scheduleId,
            @RequestBody ScheduleRequestDTO dto) {
        
    	CustomUserDetails userDetails = authService.getUserDetails();

    	dto.setScheduleId(scheduleId);
    	Long updatedId = scheduleService.updateSchedule(dto, userDetails.getId());

        return ResponseEntity.ok(
        		ApiResponse.success(ResponseCode.UPDATE_SUCCESS, updatedId, "수정이 완료되었습니다."));
    }

    /**
     *일정 삭제 (IS_ACTIVE = 'N')
     * @param scheduleId 삭제할 일정 ID
     * @return 삭제된 일정 ID와 함께 성공 응답 반환
     */
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<Long>> deleteSchedule(
    		@PathVariable(name = "scheduleId") Long scheduleId){
    	
    	CustomUserDetails userDetails = authService.getUserDetails();
    	
    	Long deletedId = scheduleService.deleteSchedule(scheduleId, userDetails.getId());
    	
    	return ResponseEntity.ok(
    			ApiResponse.success(ResponseCode.DELETE_SUCCESS, deletedId, "삭제 완료되었습니다."));
    }
	
}
