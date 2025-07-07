package com.kh.saintra.mail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.mail.model.dto.EmailDTO;
import com.kh.saintra.mail.model.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class MailController {
    
    private final MailService mailService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> sendVerificationEmail(@RequestBody @Valid EmailDTO email){
        
    	log.info("{}", email);
    	
        return ResponseEntity.ok(mailService.sendVerificationEmail(email));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> confirmEmailCode(@RequestBody @Valid EmailDTO email){
        log.info("Email verification request: {}", email);
        return ResponseEntity.ok(mailService.confirmEmailVerification(email));
    }
}
