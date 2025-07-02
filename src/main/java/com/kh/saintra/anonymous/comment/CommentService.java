package com.kh.saintra.anonymous.comment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentDto> getCommentsByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    public void writeComment(CommentDto dto) {
        commentRepository.save(dto);
    }

    public void updateComment(CommentDto dto) {
        commentRepository.update(dto);
    }

    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }
}
