package com.kh.saintra.user.model.enums;

public enum Department {
    MANAGE_TEAM("D01"),
    DEPARTMENT_TEAM("D02"),
    FINANCE_TEAM("D03"),
    CEO("D99");
    
    private final String code;

    Department(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
