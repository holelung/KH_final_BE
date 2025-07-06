package com.kh.saintra.file.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.saintra.file.model.vo.FileVO;

public interface FileService {

	void uploadFileforProfile(MultipartFile file);
	
	FileVO uploadFileforBoard(MultipartFile file);
	
	void deleteFileforProfile(String fileId);
	
	void deleteFileforBoard(Long fileId);
}
