package com.kh.saintra.anonymous.model.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CommentDto {
    private Long id;
    private Long boardId;
    private Long userId;
    private String content;
    private Date createDate;
    private String isActive;
}

