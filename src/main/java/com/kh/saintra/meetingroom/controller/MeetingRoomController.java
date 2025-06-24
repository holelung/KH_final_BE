package com.kh.saintra.meetingroom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.meetingroom.model.service.MeetingRoomService;

@RestController
@RequestMapping("/api/meetingrooms")
public class MeetingRoomController {
	
	private final MeetingRoomService meetingRoomService;
	
	// 회의실 주간 예약 조회
	@GetMapping
	public ResponseEntity<ApiResponse<MeetingRoomDTO>>
	
	
	// 회의실 예약 등

}
