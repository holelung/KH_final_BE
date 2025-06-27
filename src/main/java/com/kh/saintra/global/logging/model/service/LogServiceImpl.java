package com.kh.saintra.global.logging.model.service;

import java.beans.Transient;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kh.saintra.global.logging.model.dao.LogMapper;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.vo.Log;
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
    public List<Log> getLogs() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getLogs'");
    }
    
}
