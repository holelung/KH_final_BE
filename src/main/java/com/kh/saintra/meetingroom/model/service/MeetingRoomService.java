package com.kh.saintra.meetingroom.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kh.saintra.meetingroom.model.dto.MeetingRoomRequestDTO;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;

public interface MeetingRoomService {
	
    /**
     * 회의실 주간 예약 조회 
     * @param startDate 조회 시작 날짜 (형식: yyyy-MM-dd)
     * @param endDate   조회 종료 날짜 (형식: yyyy-MM-dd)
     * @return 해당 주간에 예약된 회의실 목록
     */
	List<MeetingRoomResponseDTO> getWeeklyReservations(String startDate, String endDate); 

    /**
     * 회의실 예약 등록
     * @param dto 사용자 입력 정보
     * @param createdBy 현재 로그인한 사용자 ID
     * @return 등록된 예약 ID
     */
    Long createReservation(MeetingRoomRequestDTO dto, Long createdBy);

}
