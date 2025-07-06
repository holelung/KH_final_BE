package com.kh.saintra.file.model.service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.saintra.file.model.dao.FileMapper;
import com.kh.saintra.file.model.dto.FileDTO;
import com.kh.saintra.file.model.vo.FileVO;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.error.exceptions.FileNotAllowedException;
import com.kh.saintra.global.error.exceptions.FileStreamException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private static final List<String> NOT_ALLOWED_FILE_EXTENSION = Arrays.asList(
		".exe", ".msi", ".bat", ".cmd", ".com", ".scr", ".pif", ".js", ".jse", ".wsf", ".vbs", ".vbe", 
		".ps1", ".psm1", ".docm", ".xlsm", ".pptm", ".php", ".php3", ".php4", ".php5", ".phtml", ".asp", 
		".aspx", ".jsp", ".jspx", ".cgi", ".pl", ".py", ".dll", ".jar", ".so", ".class", ".bin", ".hlp", 
		".cpl", ".msh", ".msh1", ".msh2", ".mshxml", ".msh1xml", ".msh2xml" 
	);
	
	private static final List<String> ALLOWED_IMAGE_EXTENSION = Arrays.asList(
		".jpg", ".jpeg", ".jpe", ".gif", ".png"
	);
	
	private final FileMapper fileMapper;
	
	/**
	 * 파일이 빈 파일인지 검사
	 * @param file
	 */
	private void checkFileisEmpty(MultipartFile file) {
		
		if(file.isEmpty()) {
			
			throw new FileStreamException(ResponseCode.INVALID_VALUE, "빈 파일은 업로드할 수 없습니다.");
		}
	}
	
	/**
	 * 파일의 확장자가 허용되었는지 검사
	 * @param origin 파일 원본명
	 * @return 파일 확장자
	 */
	private String checkFileExtension(String origin) {
	
		String extension = "";
		
		if(origin == null || !origin.contains(".")) {
			
			throw new FileStreamException(ResponseCode.INVALID_VALUE, "올바르지 않은 파일명 입니다.");
		} else {
			
			extension = origin.substring(origin.lastIndexOf(".")).toLowerCase();
		}
		
		if(NOT_ALLOWED_FILE_EXTENSION.contains(extension)) {
			
			throw new FileNotAllowedException(ResponseCode.INVALID_VALUE, "허용되지 않은 확장자 입니다.");
		}
		
		return extension;
	}
	
	/**
	 * 허용된 이미지 확장자인지 검사
	 * @param extension 파일 확장자
	 */
	private void checkImageExtension(String extension) {
		
		if(!ALLOWED_IMAGE_EXTENSION.contains(extension)) {
			
			throw new FileNotAllowedException(ResponseCode.INVALID_VALUE, "이미지 파일이 아니거나, 허용된 파일 형식이 아닙니다.");
		}
		
		return;
	}
	
	/**
	 * 서버에 업로드할 때 사용할 새로운 파일명 생성
	 * @param extension 파일 확장자
	 * @return 서버에 저장할 고유한 파일 이름
	 */
	private String createFilename(String extension) {
		UUID uuid = UUID.randomUUID();
		
		String uuidToString = Base64.getUrlEncoder()
								 	.withoutPadding()
								 	.encodeToString(uuid.toString().getBytes());
		
		String filename = uuidToString + extension;
		
		return filename;
	}
	
	/**
	 * 파일 정보 DB에 저장
	 * @param fileInfo 파일 정보
	 */
	private void insertFileInfo(FileDTO fileInfo) {
		
		if(fileMapper.insertFileInfo(fileInfo) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "데이터베이스 오류 입니다.");
		}
	}
	
	@Override
	public FileVO uploadFileforProfile(MultipartFile file) {
		// 빈 파일인지 검사
		checkFileisEmpty(file);
		
		// 파일 원본명 추출
		String origin = file.getOriginalFilename();
		
		// 파일 확장자 검사
		String extension = checkFileExtension(origin);
		
		// 이미지 파일인지 검사
		checkImageExtension(extension);
		
		// 서버에 저장할 고유한 파일 이름 생성
		String filename = createFilename(extension);
		
		// 파일 버킷에 업로드(나중에 작업)
		
		// 파일 정보 DB에 저장
		FileDTO fileInfo = new FileDTO(filename, origin);
		insertFileInfo(fileInfo);
		
		// 유니크한 파일 번호가 포함된 파일 정보 가져와서 반환
		return fileMapper.selectFileInfo(fileInfo);
	}
	
	@Override
	public FileVO uploadFileforBoard(MultipartFile file) {
		// 빈 파일인지 검사
		checkFileisEmpty(file);
		
		// 파일 원본명 추출
		String origin = file.getOriginalFilename();
		
		// 파일 확장자 검사
		String extension = checkFileExtension(origin);
		
		// 서버에 저장할 고유한 파일 이름 생성
		String filename = createFilename(extension);
		
		// 파일 버킷에 업로드(나중에 작업)
		
		// 파일 정보 DB에 저장
		FileDTO fileInfo = new FileDTO(filename, origin);
		insertFileInfo(fileInfo);
		
		// 유니크한 파일 번호가 포함된 파일 정보 가져와서 반환
		return fileMapper.selectFileInfo(fileInfo);
	}

	@Override
	public void deleteFileforProfile(String fileId) {
		// fileId를 Long 자료형으로 파싱
		Long id = (long)0;
		
		try {
			id = Long.parseLong(fileId);
			
		} catch (RuntimeException e) {
			
			throw new InvalidValueException(ResponseCode.INVALID_VALUE, "잘못된 파일 ID 입니다.");
		}
		
		// DB에 저장된 파일 정보 가져오기
		FileVO fileInfo = fileMapper.selectFileInfoById(id);
		
		// DB에 저장된 파일 정보 삭제
		if(fileMapper.deleteFileInfo(id) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "파일 삭제에 실패 했습니다.");
		}
		
		// 버킷에 저장한 url 생성(나중에 작업)
		// String filename = fileInfo.getFilename();
		
		// 버킷에 업로드된 파일 삭제(나중에 작업)
		
		// 프로필 사진을 기본 이미지로 대체
	}

	@Override
	public void deleteFileforBoard(String fileId) {
		// fileId를 Long 자료형으로 파싱
		Long id = (long)0;
		
		try {
			id = Long.parseLong(fileId);
			
		} catch (RuntimeException e) {
			
			throw new InvalidValueException(ResponseCode.INVALID_VALUE, "잘못된 파일 ID 입니다.");
		}
		
		// DB에 저장된 파일 정보 가져오기
		FileVO fileInfo = fileMapper.selectFileInfoById(id);
		
		// DB에 저장된 파일 정보 삭제
		if(fileMapper.deleteFileInfo(id) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "파일 삭제에 실패 했습니다.");
		}
		
		// 버킷에 저장한 파일 url 생성(나중에 작업)
		// String filename = fileInfo.getFilename();
		
		// 버킷에 업로드된 파일 삭제(나중에 작업)
		
	}
}
