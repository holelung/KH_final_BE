package com.kh.saintra.mail.model.service;

import com.kh.saintra.auth.model.dto.FindPasswordDTO;
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

    /**
     * <pre>
     * 비밀번호 찾기(이메일전송)
     * 
     * 사용자의 이메일로 비밀번호 변경할 수 있는 페이지의 링크를 보내줌
     * 링크에는 UUID로 생성한 KEY값이 포함되어있음
     * </pre>
     * @param email 사용자의 이메일
     * @return 
     */
    void sendPasswordFindEmail(Long id, String email);

    /**
     * <pre>
     * 가입축하 메일 전송
     * 
     * 가입 축하메일을 전송한다.
     * </pre>
     * @param email
     */
    void sendCelebrateMail(String email);

}
