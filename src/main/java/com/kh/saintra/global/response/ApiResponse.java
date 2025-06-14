package com.kh.saintra.global.response;

import com.kh.saintra.global.enums.ResponseCode;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    
    private final boolean success;
    private final String code;
    private final T data;
    private final String message;

    private ApiResponse(boolean success, ResponseCode responseCode, T data, String message) {
        this.success = success;
        this.code = responseCode.getCode();
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(ResponseCode responseCode, T data, String message) {
        return new ApiResponse<>(true, responseCode, data, message);
    }

    public static <T> ApiResponse<T> success(ResponseCode responseCode, String message) {
        return new ApiResponse<T>(true, responseCode, null, message);
    }

    public static <T> ApiResponse<T> error(ResponseCode responseCode, String message) {
        return new ApiResponse<T>(false, responseCode, null, message);
    }

}
