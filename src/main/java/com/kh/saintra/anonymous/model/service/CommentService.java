package com.kh.saintra.anonymous.model.service;

import com.kh.saintra.anonymous.model.dao.CommentMapper;
import com.kh.saintra.anonymous.model.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public List<CommentDto> getCommentsByBoardId(Long boardId) {
        return commentMapper.findByBoardId(boardId);
    }

    public void writeComment(CommentDto dto) {
        commentMapper.save(dto);
    }

    public void updateComment(CommentDto dto) {
        commentMapper.update(dto);
    }

    public void deleteComment(Long id) {
        commentMapper.delete(id);
    }
}
