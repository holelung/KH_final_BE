package com.kh.saintra.schedule.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DataAccessException;
import com.kh.saintra.global.error.exceptions.EntityNotFoundException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;
import com.kh.saintra.global.error.exceptions.UnauthorizedAccessException;
import com.kh.saintra.global.error.exceptions.UnknownException;
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
        return executeWithExceptionHandling("일정 조회", () -> scheduleMapper.getSchedules(startDate, endDate));
    }
    
    // 2. 일정 등록
    @Override
    @Transactional
    public Long createSchedule(ScheduleRequestDTO dto, Long createdBy) {
        return executeWithExceptionHandling("일정 등록", () -> {
            Long reserverId = registerReserver(dto.getReserverType());
            dto.setReserverId(reserverId);
            insertReserverMappingByType(dto, reserverId);

            DefaultColor(dto);
            DefaultEndDate(dto);
            insertSchedule(dto, createdBy);

            return dto.getScheduleId();
        });
    }

    // 3. 일정 수정
    @Override
    public Long updateSchedule(ScheduleRequestDTO dto, Long userId) {
    	return executeWithExceptionHandling("일정 수정", () -> {
    		validateSchedule(dto.getScheduleId(), userId);
    		DefaultColor(dto);
    		int result = scheduleMapper.updateSchedule(dto);
    		if (result != 1) {
    			throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "일정 수정에 실패했습니다.");
    		}
    		return dto.getScheduleId();
    	});
    }
    
    // 4. 일정 삭제 
    @Override
    @Transactional
    public Long deleteSchedule(Long scheduleId, Long userId) {
    	return executeWithExceptionHandling("일정 삭제", () -> {
    		ScheduleResponseDTO existing = scheduleMapper.findScheduleById(scheduleId);
    		if (existing == null) {
    			throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "해당 일정이 존재하지 않습니다.");
    		}
    		if (!existing.getCreatedBy().equals(userId)) {
    			throw new UnauthorizedAccessException(ResponseCode.AUTH_FAIL, "해당 일정에 대한 수정 권한이 없습니다.");
    		}
    		int result = scheduleMapper.deleteSchedule(scheduleId);
    		if (result != 1) {
    			throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "일정 삭제에 실패하였습니다.");
    		}
    		return scheduleId;
    	});
    }
    
    // 공통 예외 처리 메서드
    private <T> T executeWithExceptionHandling(String action, Supplier<T> logic) {
    	try {
    		return logic.get();
    	} catch (InvalidValueException | EntityNotFoundException |
    			DataAccessException | UnauthorizedAccessException e) {
    		log.warn("{} 실패: {}", action, e.getMessage());
    		throw e;
    	} catch (Exception e) {
    		log.error("{} 중 알 수 없는 오류 발생", action, e);
    		throw new UnknownException(ResponseCode.UNKNOWN_ERROR, action + " 중 오류가 발생했습니다.");
    	}
    }
    
    // 예약자 등록 및 ID 반환
    private Long registerReserver(String reserverType) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("reserverType", reserverType);

        int result = scheduleMapper.insertReserver(paramMap);  // scheduleMapper로 연결
        Long reserverId = (Long) paramMap.get("id");

        if (result != 1 || reserverId == null) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "예약자 정보 저장에 실패했습니다.");
        }

        return reserverId;
    }
    
    // 예약자 유형별 INSERT
    private void insertReserverMappingByType(ScheduleRequestDTO dto, Long reserverId) {
        switch (dto.getReserverType()) {
            case "USER" -> {
                if (dto.getUserId() == null) {
                    throw new InvalidValueException(ResponseCode.INVALID_VALUE, "USER 예약자는 userId가 필요합니다.");
                }
                insertUserReserver(reserverId, dto.getUserId());
            }
            case "TEAM" -> {
                if (dto.getTeamId() == null) {
                    throw new InvalidValueException(ResponseCode.INVALID_VALUE, "TEAM 예약자는 teamId가 필요합니다.");
                }
                insertTeamReserver(reserverId, dto.getTeamId());
            }
            default -> throw new InvalidValueException(ResponseCode.INVALID_VALUE, "예약자 유형은 USER 또는 TEAM이어야 합니다.");
        }
    }
    
    // 예약자 유형이 USER일 경우 INSERT
    private void insertUserReserver(Long reserverId, Long userId) {
        int result = scheduleMapper.insertUserReserver(reserverId, userId);
        if (result != 1) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "USER 예약자 저장에 실패했습니다.");
        }
    }

    // 예약자 유형이 TEAM일 경우 INSERT
    private void insertTeamReserver(Long reserverId, Long teamId) {
        int result = scheduleMapper.insertTeamReserver(reserverId, teamId);
        if (result != 1) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "TEAM 예약자 저장에 실패했습니다.");
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
        if (result != 1) {
            throw new DataAccessException(ResponseCode.DB_CONNECT_ERROR, "일정 등록에 실패했습니다.");
        }
    }

    // 일정 존재 + 권한 확인 
    private ScheduleResponseDTO validateSchedule(Long scheduleId, Long userId) {
        ScheduleResponseDTO schedule = scheduleMapper.findScheduleById(scheduleId);

        if (schedule == null) {
            throw new EntityNotFoundException(ResponseCode.ENTITY_NOT_FOUND, "해당 일정이 존재하지 않습니다.");
        }

        if (!Objects.equals(schedule.getCreatedBy(), userId)) {
            throw new UnauthorizedAccessException(ResponseCode.AUTH_FAIL, "해당 일정에 대한 수정 권한이 없습니다.");
        }

        return schedule;
    }

}
