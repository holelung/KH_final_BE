package com.kh.saintra.global.logging.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.dto.LogRequest;
import com.kh.saintra.global.logging.model.service.LogService;
import com.kh.saintra.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {
    
    private final LogService logService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<LogDTO>>> getLogs(LogRequest request){

        return ResponseEntity.ok(logService.getLogs(request));
    }


}
