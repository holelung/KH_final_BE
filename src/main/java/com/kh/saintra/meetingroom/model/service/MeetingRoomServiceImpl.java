package com.kh.saintra.meetingroom.model.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DataAccessException;
import com.kh.saintra.global.error.exceptions.DuplicateDataException;
import com.kh.saintra.global.error.exceptions.EntityNotFoundException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;
import com.kh.saintra.meetingroom.model.dao.MeetingRoomMapper;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomRequestDTO;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;
import com.kh.saintra.meetingroom.model.vo.MeetingRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingRoomServiceImpl implements MeetingRoomService{
	
	private final MeetingRoomMapper meetingRoomMapper;


	// 1. 회의실 주간 예약 조회 
	@Override
    public List<MeetingRoomResponseDTO> getWeeklyReservations(String startDate, String endDate) {
        try {
            List<MeetingRoomResponseDTO> list = meetingRoomMapper.getWeeklyReservations(startDate, endDate);
            log.debug("예약 결과 리스트: {}", list);
            return list;
        } catch (Exception e) {
            log.error("예약 목록 조회 중 예외 발생", e); 
            throw new DataAccessException(ResponseCode.SERVER_ERROR, "예약 목록을 조회하는 도중 오류가 발생했습니다.");
        }
    
	}
	
	// 2. 회의실 예약 등록 
    @Override
    @Transactional
    public Long createReservation(MeetingRoomRequestDTO dto, Long createdBy) {
    	
        // 2-1. 시간 유효성 검사
        LocalTime start = LocalTime.parse(dto.getStartTime());
        LocalTime end = LocalTime.parse(dto.getEndTime());

        if (!start.isBefore(end)) {
            throw new InvalidValueException(ResponseCode.INVALID_VALUE, "시작 시간은 종료 시간보다 이전이어야 합니다.");
        }

        // 2-2. 회의실 존재 여부 확인 
        if (meetingRoomMapper.existsMeetingRoom(dto.getRoomId()) == 0) {
            throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "회의실이 존재하지 않습니다.");

        }
        // 2-3. 예약자 존재 여부 확인
        if (meetingRoomMapper.existsReserver(dto.getReserverType(), dto.getReserverId()) == 0) {
        	throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "예약자 또는 팀이 존재하지 않습니다.");
        }
        
        // 2-4. 중복 예약 검사
        if (meetingRoomMapper.existsDuplicateReservation(dto) > 0) {
            throw new DuplicateDataException(ResponseCode.DUPLICATE_RESERVATION, "해당 시간대에 이미 예약이 존재합니다.");
        }

        // 2-5. TB_RESERVER 유형 INSERT → ID 가져오기 (얘만 VO 사용)        
        MeetingRoom reserverVo = MeetingRoom.builder()
                							.reserverType(dto.getReserverType())
                							.build();

        int reserverResult = meetingRoomMapper.insertReserver(reserverVo);
        
        Long reserverId = reserverVo.getId();

        if (reserverResult == 0 || reserverId == null) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "예약자 정보 저장에 실패했습니다.");
        }

        // 6. 예약자 유형에 따라 TB_USER_RESERVER 또는 TB_TEAM_RESERVER INSERT
        int reserverTypeResult;
        System.out.println("reservervo" + reserverVo );
        if ("USER".equals(reserverVo.getReserverType())) {
        	log.debug("insertUserReserver 호출: reserverId = {}, createdBy(userId) = {}", reserverId, createdBy);
            reserverTypeResult = meetingRoomMapper.insertUserReserver(reserverId, createdBy);
            
        } else if ("TEAM".equals(reserverVo.getReserverType())) {
        	log.debug("insertTeamReserver 호출: reserverId = {}, createdBy(teamId) = {}", reserverId, createdBy);
            reserverTypeResult = meetingRoomMapper.insertTeamReserver(reserverId, createdBy);
            
        } else {
            throw new InvalidValueException(ResponseCode.INVALID_VALUE, "예약자 유형은 'USER' 또는 'TEAM'이어야 합니다.");
        }

        if (reserverTypeResult == 0) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "예약자 유형 저장에 실패했습니다.");
        }


        // 7. TB_RESERVATION INSERT
        int reservationResult = meetingRoomMapper.insertReservation(dto, reserverId, createdBy);

        if (reservationResult == 0) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "회의실 예약 등록에 실패했습니다.");
        }

        return dto.getReservationId();
    }
}

