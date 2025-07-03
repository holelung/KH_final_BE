package com.kh.saintra.anonymous.model.service;

import com.kh.saintra.anonymous.model.dao.FileMapper;
import com.kh.saintra.anonymous.model.dto.FileDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void saveFile(FileDto dto) {
        fileMapper.save(dto);
    }

    public List<FileDto> getFilesByBoardId(Long boardId) {
        return fileMapper.findByBoardId(boardId);
    }

    public void deleteFile(Long id) {
        fileMapper.deleteById(id);
    }
}
