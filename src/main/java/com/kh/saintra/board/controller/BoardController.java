package com.kh.saintra.board.controller;

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

import com.kh.saintra.board.model.dto.BoardDeleteDTO;
import com.kh.saintra.board.model.dto.BoardDetailDTO;
import com.kh.saintra.board.model.dto.BoardInsertDTO;
import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.dto.BoardUpdateDTO;
import com.kh.saintra.board.model.service.BoardService;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/boards")
@RequiredArgsConstructor
public class BoardController {
    
	private final BoardService boardService;
	
	@GetMapping
	public ResponseEntity<?> getBoardList(@ModelAttribute @Valid BoardListDTO boardListInfo) {
		
		Map<String, Object> boardMap = boardService.getBoards(boardListInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.GET_SUCCESS, boardMap, "게시물 목록 응답 성공"));
	}
	
	@PostMapping
	public ResponseEntity<?> insertBoard(@RequestBody @Valid BoardInsertDTO boardInsertInfo) {
		
		Long boardId = boardService.insertBoard(boardInsertInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.INSERT_SUCCESS, boardId, "게시물 등록 성공"));
	}
	
	@GetMapping("/detail")
	public ResponseEntity<?> getBoardDetail(@ModelAttribute @Valid BoardDetailDTO boardDetailInfo) {
		
		Map<String, Object> boardDetail = boardService.getBoardDetail(boardDetailInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.GET_SUCCESS, boardDetail, "게시물 정보 응답 성공"));
	}
	
	@PutMapping
	public ResponseEntity<?> updateBoard(@RequestBody @Valid BoardUpdateDTO boardUpdateInfo) {

		boardService.updateBoard(boardUpdateInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "게시물 수정 성공"));
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteBoard(@ModelAttribute @Valid BoardDeleteDTO boardDeleteInfo) {

		boardService.deleteBoard(boardDeleteInfo);

		return ResponseEntity.ok(ApiResponse.success(ResponseCode.DELETE_SUCCESS, "게시물 삭제 성공"));
	}
}
