package com.kh.saintra.schedule.model.service;

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
    List<ScheduleResponseDTO> getSchedules(String startDate, String endDate);
    
    /**
     * 일정 등록 메서드
     * 
     * @param dto 일정 등록 정보
     * @param createdBy 등록자 ID (userId)
     * @return 생성된 일정의 ID
     */
    Long createSchedule(ScheduleRequestDTO dto, Long createdBy);

}
