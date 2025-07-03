package com.kh.saintra.anonymous.model.dto;

import lombok.Data;

@Data
public class FileDto {
    private Long id;
    private Long boardId;
    private String url;        // 서버에 저장된 파일명
    private String origin;     // 사용자가 업로드한 원본 이름
}
