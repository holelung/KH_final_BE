package com.kh.saintra.anonymous.model.service;

import com.kh.saintra.anonymous.model.dao.AnonymousMapper;
import com.kh.saintra.anonymous.model.dto.AnonymousDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnonymousService {

    private final AnonymousMapper anonymousMapper;

    public AnonymousService(AnonymousMapper anonymousMapper) {
        this.anonymousMapper = anonymousMapper;
    }

    public List<AnonymousDto> getBoardList() {
        return anonymousMapper.findAll();
    }

    public AnonymousDto getBoardById(Long id) {
        return anonymousMapper.findById(id);
    }

    public void writeBoard(AnonymousDto dto) {
        anonymousMapper.save(dto);
    }

    public void updateBoard(AnonymousDto dto) {
        anonymousMapper.update(dto);
    }

    public void deleteBoard(Long id) {
        anonymousMapper.delete(id);
    }

    public List<AnonymousDto> searchBoard(String keyword) {
        return anonymousMapper.search(keyword);
    }

    public List<AnonymousDto> searchMyBoard(String keyword, Long userId) {
        return anonymousMapper.searchMyBoard(keyword, userId);
    }
}
