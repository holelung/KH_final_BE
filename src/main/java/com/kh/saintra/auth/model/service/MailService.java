package com.kh.saintra.auth.model.service;

import com.kh.saintra.auth.model.dto.EmailDTO;
import com.kh.saintra.global.response.ApiResponse;

public interface MailService {

    ApiResponse<Void> sendVerificationEmail(EmailDTO email);

    ApiResponse<Void> confirmEmailVerification(EmailDTO email);
}
