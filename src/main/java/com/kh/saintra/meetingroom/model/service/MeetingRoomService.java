package com.kh.saintra.meetingroom.model.service;

import java.time.LocalDate;
import java.util.List;

import com.kh.saintra.meetingroom.model.dto.MeetingRoomRequestDTO;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;

public interface MeetingRoomService {
	
    /**
     * 회의실 주간 예약 조회 
     * @param startDate 조회 시작 날짜 (형식: yyyy-MM-dd)
     * @param endDate   조회 종료 날짜 (형식: yyyy-MM-dd)
     * @return 예약 응답 DTO 리스트
     */
	List<MeetingRoomResponseDTO> getWeeklyReservations(LocalDate startDate, LocalDate endDate); 

    /**
     * 회의실 예약 등록
     * @param dto 사용자 입력 정보
     * @param createdBy 생성자 ID (로그인한 사용자 ID)
     * @return 생성된 예약 ID
     */
    Long createReservation(MeetingRoomRequestDTO dto, Long createdBy);


    /**
     * 회의실 예약 수정 
     * @param dto 수정 요청 DTO (예약 ID 포함)
     * @param userId 요청자 ID
     * @return 수정된 예약 ID
     */
    Long updateReservation(MeetingRoomRequestDTO dto, Long userId);
    
    /**
     * 회의실 예약 삭제 
     * @param reservationId 삭제할 예약 ID
     * @param userId 요청자 ID
     * @return 삭제 처리된 예약 ID
     */
    Long deleteReservation(Long reservationId, Long userId);

    /**
     * 회의실 목록 조회
     */
    List<MeetingRoomResponseDTO> getAllRooms();

}
