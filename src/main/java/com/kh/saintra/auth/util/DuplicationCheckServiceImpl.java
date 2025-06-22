package com.kh.saintra.auth.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kh.saintra.auth.model.dao.AuthMapper;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DuplicateDataException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DuplicationCheckServiceImpl implements DuplicationCheckService{

    private final AuthMapper authMapper;


    @Override
    @Transactional(
        propagation = Propagation.REQUIRED,
        readOnly = true
    )
    public void isIdDuplicate(String username) {
        
        if( authMapper.isIdDuplicate(username) > 0 ){
            throw new DuplicateDataException(ResponseCode.DUPLICATED_ID, "해당 아이디가 존재합니다.");
        }
    }

    @Override
    public void isEmailDuplicate(String email) {
        
        if (authMapper.isEmailDuplicate(email) > 0) {
            throw new DuplicateDataException(ResponseCode.DUPLICATED_EMAIL, "해당 이메일이 존재합니다.");
        }
    }
    
}
