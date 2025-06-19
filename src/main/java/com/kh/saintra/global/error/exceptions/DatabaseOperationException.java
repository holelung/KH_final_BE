package com.kh.saintra.global.error.exceptions;

import com.kh.saintra.global.enums.ResponseCode;

public class DatabaseOperationException extends RuntimeException{
    
    private ResponseCode code;

    public DatabaseOperationException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCode getResponseCode() {
        return code;
    }
}
