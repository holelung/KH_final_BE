package com.kh.saintra.mail.model.service;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import com.kh.saintra.duplication.model.service.DuplicationCheckService;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.error.exceptions.MailServiceException;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.mail.model.dao.MailMapper;
import com.kh.saintra.mail.model.dto.EmailContent;
import com.kh.saintra.mail.model.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender mailSender;
    private final DuplicationCheckService duplicationService;
    private final MailMapper mailMapper;

    @Override
    @Transactional
    public ApiResponse<Void> sendVerificationEmail(EmailDTO email) {
        // 이메일 중복검사
        duplicationService.isEmailDuplicate(email.getEmail());
        // 인증코드 생성
        email.setVerifyCode(createVerifyCode());
        EmailContent content = createVerificationEmail(email);
        
        try {
            mailMapper.insertVerifyCode(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "인증코드 저장 실패");
        }

        sendMail(email, content);
        
        return ApiResponse.success(ResponseCode.MAIL_SEND, "Complete Mail send");
    }

    @Override
    public ApiResponse<Void> confirmEmailVerification(EmailDTO email) {
        
        // DB에서 일치하는게 있는지 확인


        return ApiResponse.success(ResponseCode.MAIL_VERIFIED, "이메일 인증 성공");
    }

    @Override
    public ApiResponse<Void> sendPasswordFindEmail(EmailDTO email) {
        
        throw new UnsupportedOperationException("Unimplemented method 'sendPasswordFindEmail'");
    }
    

    // 인증코드 생성
    private String createVerifyCode(){
        int length = 6; 
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        SecureRandom secureRandom = new SecureRandom();

        for(int i=0; i<length; i++){
            int index = secureRandom.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        
        return sb.toString();
    }

    // 인증 메일 내용 생성
    private EmailContent createVerificationEmail(EmailDTO email){
        EmailContent content = new EmailContent();
        content.setSubject("Saintra 이메일 인증번호 입니다.");

        String html;
        try {
            ClassPathResource res = new ClassPathResource("templates/mail/verificationCode.html");
            html = StreamUtils.copyToString(res.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new MailServiceException(ResponseCode.MAIL_TEMPLATE_ERROR, "이메일 템플릿 로드 실패" + e);
        }

        html = html.replace("{{verifyCode}}", email.getVerifyCode());
        content.setContent(html);
        return content;
    }

    // 가입 승인 메일 내용 생성
    private EmailContent createNotificationEmail(EmailDTO email){

        return null;
    }

    // 비밀번호 찾기 메일 내용생성
    private EmailContent createFindPasswordEmail(EmailDTO email){
        return null;
    }

    // 메일 전송
    private void sendMail(EmailDTO email, EmailContent content){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mailHelper = new MimeMessageHelper(message, true, "UTF-8");
            mailHelper.setTo(email.getEmail());
            mailHelper.setSubject(content.getSubject());
            mailHelper.setText(content.getContent(), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
