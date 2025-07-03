package com.kh.saintra.anonymous.controller;

import com.kh.saintra.anonymous.model.dto.CommentDto;
import com.kh.saintra.anonymous.model.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class AnonymousCommentController {

    private final CommentService commentService;

    public AnonymousCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDto> getComments(@RequestParam Long boardId) {
        return commentService.getCommentsByBoardId(boardId);
    }

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentDto dto) {
        commentService.writeComment(dto);
        return ResponseEntity.ok("댓글 등록 완료");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentDto dto) {
        dto.setId(id);
        commentService.updateComment(dto);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("댓글 삭제 완료");
    }
}
