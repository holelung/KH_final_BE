package com.kh.saintra.anonymous.model.dto;

import lombok.Data;
import java.util.Date;

@Data
public class FileDto {
    private Long id;
    private Long boardId;
    private String fileName;       // 서버에 저장된 파일명
    private String originalName;   // 사용자가 업로드한 원본 이름
    private String filePath;       // 서버 저장 경로
    private Date uploadDate;
    private String isActive;
}
