package com.kh.saintra.anonymous.board;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnonymousService {

    private final AnonymousRepository AnonymousRepository;

    public AnonymousService(AnonymousRepository AnonymousRepository) {
        this.AnonymousRepository = AnonymousRepository;
    }

    public List<AnonymousDto> getBoardList() {
        return AnonymousRepository.findAll();
    }
    
    public List<AnonymousDto> searchBoard(String keyword) {
        return AnonymousRepository.search(keyword);
    }

    public AnonymousDto getBoardById(Long id) {
        return AnonymousRepository.findById(id);
    }

    public void writeBoard(AnonymousDto dto) {
    	AnonymousRepository.save(dto);
    }

    public void updateBoard(AnonymousDto dto) {
    	AnonymousRepository.update(dto);
    }

    public void deleteBoard(Long id) {
    	AnonymousRepository.delete(id);
    }

    public List<AnonymousDto> searchMyBoard(String keyword, Long userId) {
        return AnonymousRepository.searchMyBoard(keyword, userId);
    }

}
