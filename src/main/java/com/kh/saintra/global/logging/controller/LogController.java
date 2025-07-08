package com.kh.saintra.global.logging.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.dto.LogRequest;
import com.kh.saintra.global.logging.model.service.LogService;
import com.kh.saintra.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {
    
    private final LogService logService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLogs(@ModelAttribute LogRequest request){

        log.info("Log 요청 {}", request);
        return ResponseEntity.ok(logService.getLogs(request));
    }


}
