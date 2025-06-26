package com.kh.saintra.meetingroom.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.saintra.meetingroom.model.dto.MeetingRoomRequestDTO;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;
import com.kh.saintra.meetingroom.model.vo.MeetingRoom;


@Mapper
public interface MeetingRoomMapper {

	
	// 1. 주간 예약 조회
	/*
	 * (startDate ~ endDate) 동안의 회의실 예약 조회
	 * 
	 * @param startDate 조회 시작일 
	 * @param endDate 조회 종료일 
	 * @return 예약 목록 리스트 
	 */
	List<MeetingRoomResponseDTO> getWeeklyReservations(@Param("startDate") String startDate,
												  	   @Param("endDate") String endDate);
	
	// 2. 회의실 예약 등록 
	
	// 2-2. 회의실 존재 여부 확인 
	int existsMeetingRoom(@Param("roomId") Long roomId);

	// 2-3. 예약자 존재 여부 확인 
	int existsReserver(@Param("reserverType") String reserverType,
            		   @Param("reserverId") Long reserverId);

	// 2-4. 중복 예약 검사 
	int existsDuplicateReservation(@Param("dto") MeetingRoomRequestDTO dto);
	
	// 2-5. 예약자 타입 등록 
	int insertReserver(MeetingRoom vo);

	// 2-6. 예약자 유형별 등록
    int insertUserReserver(@Param("reserverId")Long reserverId, @Param("userId") Long userId);
    int insertTeamReserver(@Param("reserverId")Long reserverId, @Param("teamId") Long teamId);

    // 2-7. 예약 등록 
    int insertReservation(@Param("dto") MeetingRoomRequestDTO dto,
            @Param("reserverId") Long reserverId,
            @Param("createdBy") Long createdBy);
	
	
}
