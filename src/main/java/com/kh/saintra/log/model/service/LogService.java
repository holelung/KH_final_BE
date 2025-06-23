package com.kh.saintra.log.model.service;

import java.util.List;

import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.log.model.dto.LogDTO;
import com.kh.saintra.log.model.dto.LogRequestDTO;

public interface LogService {

    boolean insertLog(LogDTO log);

    ApiResponse<List<LogDTO>> getLogs(LogRequestDTO logRequest);
}



