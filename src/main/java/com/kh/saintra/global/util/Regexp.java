package com.kh.saintra.global.util;

public final class Regexp {

    private Regexp() {
        throw new UnsupportedOperationException("이거 유틸 클래스임!");
    }

    public static final String USERNAME = "^[0-9]{8,12}$";
    public static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$";
    public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String REAL_NAME = "^[가-힣]{2,6}$";
    public static final String PHONE = "^010-\\d{4}-\\d{4}$";
    public static final String SSN = "^\\d{6}-[1-4]\\d{6}$";
    public static final String VERIFY_CODE = "^[A-Z0-9]{6}";
    public static final String DATE = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String TEXT = "^[가-힣a-zA-Z0-9\\s!@#$%^&*(),.?\":{}|<>_-]*$";
    public static final String COLOR = "^#([A-Fa-f0-9]{6})$";
    
}
