package com.kh.saintra.mail.model.dao;

import org.apache.ibatis.annotations.Mapper;
import com.kh.saintra.mail.model.dto.EmailDTO;

@Mapper
public interface MailMapper {
    
    void insertVerifyCode(EmailDTO email);

    int selectVerifyCode(EmailDTO email);
}
