package com.kh.saintra.global.logging.model.service;

import java.util.List;
import java.util.Map;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.dto.LogRequest;
import com.kh.saintra.global.logging.model.vo.Log;
import com.kh.saintra.global.response.ApiResponse;

public interface LogService {

    /**
     * Log 저장
     * @param LogDTO log
     */
    void saveLog(LogDTO log);

    /**
     * Log 조회
     * @return List<Log>
     */
    ApiResponse<Map<String, Object>> getLogs(LogRequest request);
}
