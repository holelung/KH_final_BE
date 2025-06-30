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
    
    // 2-2. 예약자 존재 확인 
	int existsReserver(@Param("reserverType") String reserverType,
					   @Param("reserverId") Long reserverId);
	
	// 2-3. 예약자 유형 등록
    int insertReserver(Map<String, Object> paramMap);

	// 2-4. 예약자 유형별 등록
    int insertUserReserver(@Param("reserverId")Long reserverId,
    					   @Param("userId") Long userId);
    
    int insertTeamReserver(@Param("reserverId")Long reserverId,
    					   @Param("teamId") Long teamId);

}
