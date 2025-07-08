package com.kh.saintra.global.enums;

public enum ResponseCode {
    GET_SUCCESS("S200"),
    INSERT_SUCCESS("S201"),
    UPDATE_SUCCESS("S202"),
    DELETE_SUCCESS("S203"),
    LOGIN_SUCCESS("S100"),
    MAIL_SEND("S101"),
    MAIL_VERIFIED("S102"),
    DUPLICATED_ID("E100"),
    DUPLICATED_EMAIL("E101"),
    INVALID_USERDATA("E102"),
    MAIL_SEND_FAIL("E103"),
    VERIFIED_TIMEOUT("E104"),
    VERIFIED_FAIL("E105"),
    INVALID_ACCESS("E106"),
    DUPLICATED_PASSWORD("E107"),
    MAIL_TEMPLATE_ERROR("E108"),
    INVALID_VALUE("E400"),
    AUTH_FAIL("E401"),
    ENTITY_NOT_FOUND("E103"),
    SERVER_ERROR("E500"), 
    DB_CONNECT_ERROR("E501"),
    SQL_ERROR("E502"),
    DUPLICATE_RESERVATION("E600"),
    EXPIRED_TOKEN("E900"),
    INVALID_TOKEN("E901"),
	UNKNOWN_ERROR("E999");



    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() { 
        return code;
    }
}
