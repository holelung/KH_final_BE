package com.kh.saintra.schedule.model.service;

import java.time.LocalDate;
import java.util.List;

import com.kh.saintra.schedule.model.dto.ScheduleRequestDTO;
import com.kh.saintra.schedule.model.dto.ScheduleResponseDTO;

public interface ScheduleService {

	/**
     * 일정 목록 조회 (주 단위 또는 월 단위)
     * @param startDate 시작일 (yyyy-MM-dd)
     * @param endDate 종료일 (yyyy-MM-dd)
     * @return 일정 리스트
     */
    List<ScheduleResponseDTO> getSchedules(LocalDate startDate, LocalDate endDate);

    /**
     * 일정 등록 
     * 
     * @param dto 일정 등록 정보
     * @param createdBy 등록자 ID (userId)
     * @return 생성된 일정의 ID
     */
    Long createSchedule(ScheduleRequestDTO dto, Long createdBy);
    
    /**
     * 일정 수정
     * 
     * @param dto 일정 수정 정보
     * @param userId 사용자 ID (소유자 확인용)
     * @return 수정된 일정 ID
     */
    Long updateSchedule(ScheduleRequestDTO dto, Long userId);

    /**
     * 일정 삭제
	 * @param scheduleId 삭제할 일정 ID
	 * @param userId 사용자 ID (일정 소유자 확인용)
	 * @return 삭제된 일정 ID
     */
    Long deleteSchedule(Long scheduleId, Long userId);

}
