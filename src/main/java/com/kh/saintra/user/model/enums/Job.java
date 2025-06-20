package com.kh.saintra.user.model.enums;

public enum Job {
    NEW_EMPLOYEE("J01"),
    SENIOR("J02"),
    CEO("J99");

    private final String code;

    Job(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
