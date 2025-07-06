package com.kh.saintra.file.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.saintra.file.model.dto.FileDTO;
import com.kh.saintra.file.model.vo.FileVO;

@Mapper
public interface FileMapper {

	FileVO getProfile(Long userId);
	
	int insertProfileInfo(FileDTO fileInfo);
	
	int insertFileInfo(FileDTO fileInfo);
	
	FileVO selectFileInfo(FileDTO fileInfo);
	
	FileVO selectFileInfoById(Long fileId);
	
	int deleteFileInfo(Long fileId);
}
