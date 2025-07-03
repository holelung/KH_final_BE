package com.kh.saintra.anonymous.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AnonymousDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Date createDate;
    private String isActive;
}

