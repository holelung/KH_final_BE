package com.kh.saintra.file.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.saintra.file.model.dto.FileDTO;
import com.kh.saintra.file.model.vo.FileVO;
import com.kh.saintra.file.model.vo.ProfileVO;

@Mapper
public interface FileMapper {

	ProfileVO getProfile(Long userId);
	
	int updateProfileInfo(FileDTO fileInfo);
	
	int insertFileInfo(FileDTO fileInfo);
	
	FileVO selectFileInfo(FileDTO fileInfo);
	
	FileVO selectFileInfoByFileId(Long fileId);
	
	int resetProfile(Long userId);
	
	int deleteFileInfo(Long fileId);
}
