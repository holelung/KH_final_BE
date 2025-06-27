package com.kh.saintra.global.logging.model.service;

import java.beans.Transient;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.logging.model.dao.LogMapper;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.dto.LogRequest;
import com.kh.saintra.global.logging.model.vo.Log;
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
    public ApiResponse<List<LogDTO>> getLogs(LogRequest request) {
        
        List<LogDTO> result = logMapper.selectLogList();
        
        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "로그 조회 성공");
    }
    
}
