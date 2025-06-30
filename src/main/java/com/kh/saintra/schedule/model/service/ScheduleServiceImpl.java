package com.kh.saintra.schedule.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DataAccessException;
import com.kh.saintra.global.error.exceptions.EntityNotFoundException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;
import com.kh.saintra.schedule.model.dao.ScheduleMapper;
import com.kh.saintra.schedule.model.dto.ScheduleRequestDTO;
import com.kh.saintra.schedule.model.dto.ScheduleResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

	private final ScheduleMapper scheduleMapper;

	// 1. 일정 조회 
    @Override
    public List<ScheduleResponseDTO> getSchedules(String startDate, String endDate) {
        try {
            return scheduleMapper.getSchedules(startDate, endDate);
        } catch (Exception e) {
            log.error("일정 목록 조회 중 오류 발생", e);
            throw new DataAccessException(ResponseCode.SERVER_ERROR, "일정 조회 중 오류가 발생했습니다.");
        }
    }
    
    // 2. 일정 등록 
    @Override
    @Transactional
    public Long createSchedule(ScheduleRequestDTO dto, Long createdBy) {
    	
        checkReserverExists(dto.getReserverType(), dto.getReserverId());
        
        Long reserverId = registerReserver(dto.getReserverType());
        dto.setReserverId(reserverId);
        insertReserverByType(dto.getReserverType(), reserverId, createdBy);
        
        DefaultColor(dto);
        DefaultEndDate(dto);

        insertSchedule(dto, createdBy);

        return dto.getScheduleId();
    }

    // 예약자 존재 여부 확인
    private void checkReserverExists(String reserverType, Long reserverId) {
    if (scheduleMapper.existsReserver(reserverType, reserverId) == 0) {
    	throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "예약자 또는 팀이 존재하지 않습니다.");
    	}
    }
    
    // 예약자 등록 및 ID 반환
    private Long registerReserver(String reserverType) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("reserverType", reserverType);

        int result = scheduleMapper.insertReserver(paramMap);  // scheduleMapper로 연결
        Long reserverId = (Long) paramMap.get("id");

        if (result == 0 || reserverId == null) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "예약자 정보 저장에 실패했습니다.");
        }

        return reserverId;
    }
    
    // 예약자 유형별 INSERT
    private void insertReserverByType(String reserverType, Long reserverId, Long createdBy) {
        int result = 0;

        if ("USER".equals(reserverType)) {
            log.debug("insertUserReserver 호출: reserverId = {}, userId = {}", reserverId, createdBy);
            result = scheduleMapper.insertUserReserver(reserverId, createdBy);
        } else if ("TEAM".equals(reserverType)) {
            log.debug("insertTeamReserver 호출: reserverId = {}, teamId = {}", reserverId, createdBy);
            result = scheduleMapper.insertTeamReserver(reserverId, createdBy);
        } else {
            throw new InvalidValueException(ResponseCode.INVALID_VALUE, "예약자 유형은 'USER' 또는 'TEAM'이어야 합니다.");
        }

        if (result == 0) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "예약자 유형 저장에 실패했습니다.");
        }
    }

    
    // 색상 코드 기본값 지정
    private void DefaultColor(ScheduleRequestDTO dto) {
    	
        if (dto.getColorCode() == null || dto.getColorCode().isBlank()) {
            dto.setColorCode("#2196F3");
        }
    }
    
    // 종료일이 없을 경우 시작일로 설정
    private void DefaultEndDate(ScheduleRequestDTO dto) {
    	
        if (dto.getEndDate() == null || dto.getEndDate().isBlank()) {
            dto.setEndDate(dto.getStartDate());
        }
    }
    
    // 일정 등록
    private void insertSchedule(ScheduleRequestDTO dto, Long createdBy) {
        int result = scheduleMapper.insertSchedule(dto, dto.getReserverId(), createdBy);
        if (result == 0) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "일정 등록에 실패했습니다.");
        }
    }
	

}
