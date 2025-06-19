package com.kh.saintra.global.error.exceptions;

import com.kh.saintra.global.enums.ResponseCode;

public class DuplicateDataException extends RuntimeException {
    
    private ResponseCode code;

    public DuplicateDataException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCode getResponseCode() {
        return code;
    } 
}
