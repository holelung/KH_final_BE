package com.kh.saintra.anonymous.file;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    // 설정파일 없이 직접 경로 지정
    private final String uploadDir = "C:/upload/anonymous";

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file,
                                             @RequestParam Long boardId) {
        try {
            saveFile(file, boardId);
            return ResponseEntity.ok("파일 업로드 성공");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
        }
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<String> uploadMultiple(@RequestParam("files") List<MultipartFile> files,
                                                 @RequestParam Long boardId) {
        try {
            for (MultipartFile file : files) {
                saveFile(file, boardId);
            }
            return ResponseEntity.ok("모든 파일 업로드 완료");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("파일 업로드 중 오류 발생: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getFiles(@RequestParam Long boardId) {
        return ResponseEntity.ok(fileService.getFilesByBoardId(boardId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok("파일 삭제 완료");
    }

    // 파일 저장 메서드
    private void saveFile(MultipartFile file, Long boardId) throws Exception {
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
        }

        // UUID를 통한 저장 파일명 생성
        String savedName = UUID.randomUUID() + "_" + originalName;
        String savePath = Paths.get(uploadDir, savedName).toString();

        // 파일 저장
        file.transferTo(new File(savePath));

        // DB 저장용 DTO 생성
        FileDto dto = new FileDto();
        dto.setBoardId(boardId);
        dto.setFileName(savedName);
        dto.setOriginalName(originalName);
        dto.setFilePath(savePath);

        fileService.saveFile(dto);
    }
}
