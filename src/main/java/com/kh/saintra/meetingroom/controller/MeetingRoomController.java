package com.kh.saintra.meetingroom.controller;

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
import com.kh.saintra.global.error.exceptions.UnauthorizedAccessException;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomRequestDTO;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;
import com.kh.saintra.meetingroom.model.service.MeetingRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetingrooms")
public class MeetingRoomController {
	
	private final MeetingRoomService meetingRoomService;
	private final AuthService authService;

	
	/**
	 * 회의실 주간 예약 조회 
	 * @param startDate (yyyy-MM-dd)
	 * @param endDate (yyyy-MM-dd)
	 * @return 예약목록 
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<List<MeetingRoomResponseDTO>>> getWeeklyReservations(
			@RequestParam(name = "startDate") String startDate,
			@RequestParam(name = "endDate") String endDate
			){
		List<MeetingRoomResponseDTO> reservations = meetingRoomService.getWeeklyReservations(startDate, endDate);
		
		return ResponseEntity.ok(
				ApiResponse.success(ResponseCode.GET_SUCCESS, reservations, "예약 목록 조회에 성공했습니다.")
				);
	}
	/**
	 * 회의실 예약 등록 
	 * @param dto 예약정보 DTO
	 * @param userDetails 로그인한 사용자 정보 
	 * @return 생성된 예약 ID
	 */
    @PostMapping("/write")
    public ResponseEntity<ApiResponse<Long>> createReservation(
            @Validated @RequestBody MeetingRoomRequestDTO dto){
    	
    	CustomUserDetails userDetails = authService.getUserDetails();
    	// -> getUserDetails 수정 예시 (일단 얘만 수정하고 다른 부분들은 추후 수정 예정)
    	
        Long createdBy = userDetails.getId();
        Long reservationId = meetingRoomService.createReservation(dto, createdBy);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.INSERT_SUCCESS, reservationId, "예약이 등록되었습니다."));
    }
    
    /**
     * 회의실 예약 수정 
     * @param reservationId 수정할 예약 ID	
     * @param dto 수정할 예약 정보
     * @param useerDetails 로그인 한 사용자 정보 
     * @return 수정된 예약 ID
     */
    @PutMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<Long>> updateReservation(
    		@PathVariable(name = "reservationId") Long reservationId,
    		@RequestBody MeetingRoomRequestDTO dto,
    		@AuthenticationPrincipal CustomUserDetails userDetails) {
    	
        dto.setReservationId(reservationId);
        Long updatedId = meetingRoomService.updateReservation(dto, userDetails.getId());

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.UPDATE_SUCCESS, updatedId, "수정이 완료되었습니다."));
    }
    
    /**
     * 회의실 예약 삭제 (IS_ACTIVE = 'N')
     * @param reservationId 삭제할 예약 ID
     * @param userDetails 로그인한 사용자 정보 
     * @return 삭제 처리한 예약 ID
     */
    @PatchMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<Long>> deleteReservation(
    		@PathVariable(name = "reservationId") Long reservationId,
    		@AuthenticationPrincipal CustomUserDetails userDetails){
    	
    	Long delectedId = meetingRoomService.deleteReservation(reservationId, userDetails.getId());
    	
    	return ResponseEntity.ok(
    			ApiResponse.success(ResponseCode.DELETE_SUCCESS, delectedId, "삭제 완료되었습니다."));
    }
    

}
