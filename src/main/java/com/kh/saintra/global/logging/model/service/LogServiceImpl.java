package com.kh.saintra.global.logging.model.service;

import java.beans.Transient;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.logging.model.dao.LogMapper;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.dto.LogRequest;
import com.kh.saintra.global.logging.model.vo.Log;
import com.kh.saintra.global.logging.model.vo.LogVo;
import com.kh.saintra.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

    private final LogMapper logMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(LogDTO log) {
        try {
            logMapper.insertLog(log);
        } catch (Exception e) {
            System.err.println("Log insert failed: "+ e.getMessage());
        }
    }

    @Override
    public ApiResponse<Map<String, Object>> getLogs(LogRequest request) {
       
        request.setCurrentPage(request.getCurrentPage() * request.getRowsPerPage());
        // LogVo convertRequest = LogVo.builder()
        //                                 .startDate(LocalDateTime.(Timestamp.valueOf(request.getStartDate()))
        //                                 .endDate(Timestamp.valueOf(request.getEndDate()))
        //                                 .currentPage(request.getCurrentPage())
        //                                 .rowsPerPage(request.getRowsPerPage())
        //                                 .search(request.getSearch())
        //                                 .build();

        

        Map<String, Object> result = new HashMap<>();
        
        try {
            result.put("list", logMapper.selectLogList(request));
            result.put("total", logMapper.selectTotalCount(request));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "로그 조회 실패");
        }
        
        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "로그 조회 성공");
    }
    
}
