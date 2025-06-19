package com.kh.saintra.global.error.exceptions;

import com.kh.saintra.global.enums.ResponseCode;

public class FileStreamException extends RuntimeException {
    
    private ResponseCode code;

    public FileStreamException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCode getResponseCode() {
        return code;
    }
    
}
