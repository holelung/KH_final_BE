package com.kh.saintra.meetingroom.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;

public interface MeetingRoomService {
	
	// 주간 예약 조회 
	List<MeetingRoomResponseDTO> getWeeklyReservations(@Param("startDate")String startDate, @Param("endDate")String endDate); 


}
