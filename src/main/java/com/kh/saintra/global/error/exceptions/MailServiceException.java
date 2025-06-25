package com.kh.saintra.global.error.exceptions;

import com.kh.saintra.global.enums.ResponseCode;
import jakarta.mail.MessagingException;

public class MailServiceException extends RuntimeException{

    private ResponseCode code;

    public MailServiceException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCode getResponseCode() {
        return code;
    }
    
    
}
