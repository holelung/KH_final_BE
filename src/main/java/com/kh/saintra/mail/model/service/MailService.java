package com.kh.saintra.mail.model.service;

import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.mail.model.dto.EmailDTO;

public interface MailService {
    
    /**
     * <pre>
     * 인증메일 전송
     * 회원가입/ 이메일변경 시 사용함
     * <strong>내부로직</strong>
     * 1. 이메일 중복검사
     * 2. 인증코드 생성
     * 3. 인증코드 테이블에 저장
     * 4. 인증메일 생성
     * 5. 인증메일 전송
     * </pre>
     * 
     * @param email 사용자 이메일
     * @return ApiResponse<Void>
     */
    ApiResponse<Void> sendVerificationEmail(EmailDTO email);

    /**
     * <pre>
     * 인증코드 확인
     * 1. 인증코드를 받아서 데이터베이스의 값과 대조
     * </pre>
     * @param email 사용자 이메일, 인증 코드
     * @return ApiResponse<Void> 
     */
    ApiResponse<Void> confirmEmailVerification(EmailDTO email);

    ApiResponse<Void> sendPasswordFindEmail(EmailDTO email);
}
