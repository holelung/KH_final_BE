package com.kh.saintra.meetingroom.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DataAccessException;
import com.kh.saintra.meetingroom.model.dao.MeetingRoomMapper;
import com.kh.saintra.meetingroom.model.dto.MeetingRoomResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingRoomServiceImpl implements MeetingRoomService{
	
	private final MeetingRoomMapper meetingRoomMapper;


	// 주간 예약 조회 
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

}
