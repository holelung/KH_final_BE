package com.kh.saintra.meetingroom.model.service;

import java.util.List;

import com.kh.saintra.meetingroom.model.dto.MeetingRoomDTO;

public interface MeetingRoomService {
	
	List<MeetingRoomDTO> getWeeklyReservations(String startDate, String endDate); // 주간 예약 조회 


}
