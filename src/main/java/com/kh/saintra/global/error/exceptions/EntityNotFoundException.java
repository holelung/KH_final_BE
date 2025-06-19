package com.kh.saintra.global.error.exceptions;

import com.kh.saintra.global.enums.ResponseCode;

public class EntityNotFoundException extends RuntimeException {
    
    private ResponseCode code;

    public EntityNotFoundException(ResponseCode code, String message){
        super(message);
        this.code = code;
    };

    public ResponseCode getResponseCode() {
        return code;
    }
}
