package com.kh.saintra.meetingroom.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;


@Mapper
public interface MeetingRoomMapper {

	
	// 주간 예약 조회
	/*
	 * (startDate ~ endDate) 동안의 회의실 예약 조회
	 * 
	 * @param startDate 조회 시작일 
	 * @param endDate 조회 종료일 
	 * @return 예약 목록 리스트 
	 */
	List<MeetingRoomResponseDTO> getWeeklyReservations(@Param("startDate") String startDate,
												  @Param("endDate") String endDate);

	
}
