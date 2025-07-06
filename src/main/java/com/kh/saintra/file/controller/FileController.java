package com.kh.saintra.file.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.saintra.file.model.service.FileService;
import com.kh.saintra.file.model.vo.FileVO;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/files")
@RequiredArgsConstructor
public class FileController {
	
	private final FileService fileService;
	
	@GetMapping("/users")
	@Transactional
	public ResponseEntity<?> getProfile() {
		
		FileVO profile = fileService.getProfile();
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.INSERT_SUCCESS, profile, "프로필 가져오기 성공"));
	}
	
	/**
	 * 사용자 프로필에 이미지 파일을 삽입하면 파일을 서버에 업로드하고, 업로드한 파일 정보를 반환하는 메서드
	 * @param file 업로드할 파일
	 * @return DB에 저장된 파일 정보
	 */
	@PostMapping("/users")
	@Transactional
	public ResponseEntity<?> uploadFileforProfile(@RequestParam(name = "file") MultipartFile file) {
		
		fileService.uploadFileforProfile(file);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.INSERT_SUCCESS, "프로필 사진 업로드 성공"));
	}

	/**
	 * 문서 편집기에 파일을 삽입하면 요청을 보내 파일을 서버에 업로드하고, 업로드한 파일 정보를 반환하는 메서드
	 * @param file 업로드할 파일
	 * @return DB에 저장된 파일 정보
	 */
	@PostMapping("/boards")
	public ResponseEntity<?> uploadFileforBoard(@RequestParam(name = "file") MultipartFile file) {
		
		FileVO fileInfo = fileService.uploadFileforBoard(file);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.INSERT_SUCCESS, fileInfo, "게시물 첨부 파일 업로드 성공"));
	}
	
	/**
	 * 사용자 프로필에서 이미지 파일을 제거하면, 서버의 파일을 삭제하고 기본 이미지로 교체하는 메서드
	 * @param fileId 서버에 업로드된 파일의 DB에 저장된 고유 번호
	 * @return
	 */
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteFileforProfile(@RequestParam(name = "fileId") Long fileId) {
		
		fileService.deleteFileforProfile(fileId);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.DELETE_SUCCESS, "프로필 사진 삭제 성공"));
	}
	
	/**
	 * 문서 편집기에서 파일을 제거하면, 서버의 파일을 삭제하는 메서드
	 * @param fileId 서버에 업로드된 파일의 DB에 저장된 고유 번호
	 * @return
	 */
	@DeleteMapping("/boards")
	public ResponseEntity<?> deleteFileforBoard(@RequestParam(name = "fileId") Long fileId) {
		
		fileService.deleteFileforBoard(fileId);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.DELETE_SUCCESS, "게시물 첨부 파일 삭제 성공"));
	}
}
