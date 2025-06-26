package com.kh.saintra.meetingroom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	/**
	 * 회의실 주간 예약 조회 
	 * @param startDate
	 * @param endDate
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
	 * @param dto
	 * @param userDetails
	 */
    @PostMapping("/write")
    public ResponseEntity<ApiResponse<Long>> createReservation(
            @Validated @RequestBody MeetingRoomRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
    	
    	if (userDetails == null) {
            throw new UnauthorizedAccessException(ResponseCode.AUTH_FAIL, "로그인이 필요합니다.");
        }
    	
        Long createdBy = userDetails.getId();
        Long reservationId = meetingRoomService.createReservation(dto, createdBy);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.INSERT_SUCCESS, reservationId, "예약이 등록되었습니다."));
    }
}
