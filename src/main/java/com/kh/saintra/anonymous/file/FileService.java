package com.kh.saintra.anonymous.file;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void saveFile(FileDto dto) {
        fileRepository.save(dto);
    }

    public List<FileDto> getFilesByBoardId(Long boardId) {
        return fileRepository.findByBoardId(boardId);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}
