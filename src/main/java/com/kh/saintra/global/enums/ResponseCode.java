package com.kh.saintra.global.enums;

public enum ResponseCode {
    SUCCESS("S200"),
    SERVER_ERROR("E500");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
