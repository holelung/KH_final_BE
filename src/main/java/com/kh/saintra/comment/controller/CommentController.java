package com.kh.saintra.comment.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.comment.model.dto.CommentDeleteDTO;
import com.kh.saintra.comment.model.dto.CommentInsertDTO;
import com.kh.saintra.comment.model.dto.CommentListDTO;
import com.kh.saintra.comment.model.dto.CommentUpdateDTO;
import com.kh.saintra.comment.model.service.CommentService;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {
    
	private final CommentService commentService;
	
	@GetMapping
	@Transactional
	public ResponseEntity<?> getCommentList(@ModelAttribute @Valid CommentListDTO commentListInfo) {
		
		Map<String, Object> commentMap = commentService.getCommentList(commentListInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.GET_SUCCESS, commentMap, "댓글 목록 응답 성공"));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> insertComment(@RequestBody @Valid CommentInsertDTO commentInsertInfo) {
		
		commentService.insertComment(commentInsertInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.INSERT_SUCCESS, "댓글 등록 성공"));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> updateComment(@RequestBody @Valid CommentUpdateDTO commentUpdateInfo) {
		
		commentService.updateComment(commentUpdateInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "댓글 수정 성공"));
	}
	
	@DeleteMapping
	@Transactional
	public ResponseEntity<?> deleteComment(@RequestBody @Valid CommentDeleteDTO commentDeleteInfo) {
		
		commentService.deleteComment(commentDeleteInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.DELETE_SUCCESS, "댓글 삭제 성공"));
	}
}
