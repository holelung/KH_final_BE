package com.kh.saintra.schedule.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.saintra.schedule.model.dto.ScheduleRequestDTO;
import com.kh.saintra.schedule.model.dto.ScheduleResponseDTO;

@Mapper
public interface ScheduleMapper {
	
	// 1. 일정 조회 
    List<ScheduleResponseDTO> getSchedules(@Param("startDate") String startDate,
                                                  @Param("endDate") String endDate);

    // 2. 일정 등록
    int insertSchedule(@Param("dto") ScheduleRequestDTO dto, 
            		   @Param("reserverId") Long reserverId, 
            		   @Param("createdBy") Long createdBy);
	
	// 2-3. 예약자 유형 등록
    int insertReserver(Map<String, Object> paramMap);

	// 2-4. 예약자 유형별 등록
    int insertUserReserver(@Param("reserverId")Long reserverId,
    					   @Param("userId") Long userId);
    
    int insertTeamReserver(@Param("reserverId")Long reserverId,
    					   @Param("teamId") Long teamId);

    // 3. 일정 수정
    int updateSchedule(ScheduleRequestDTO dto);

    // 3-2. 수정 시 일정 조회 
    ScheduleResponseDTO findScheduleById(@Param ("scheduleId") Long scheduleId);
    
    // 4. 일정 삭제 
    int deleteSchedule(Long scheduleId);
}
