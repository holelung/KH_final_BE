package com.kh.saintra.mail.model.dao;

import org.apache.ibatis.annotations.Mapper;
import com.kh.saintra.auth.model.dto.FindPasswordDTO;
import com.kh.saintra.mail.model.dto.EmailDTO;

@Mapper
public interface MailMapper {
    
    void insertVerifyCode(EmailDTO email);

    int selectVerifyCode(EmailDTO email);

    void insertAccessKey(FindPasswordDTO findPasswordDTO);
}
