package com.kh.saintra.file.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.saintra.file.model.vo.FileVO;
import com.kh.saintra.file.model.vo.ProfileVO;

public interface FileService {

	ProfileVO getProfile();
	
	void uploadFileforProfile(MultipartFile file);
	
	FileVO uploadFileforBoard(MultipartFile file);
	
	void deleteFileforProfile();
	
	void deleteFileforBoard(Long fileId);
}
