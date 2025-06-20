package com.kh.saintra.board.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.service.BoardService;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/boards")
@RequiredArgsConstructor
public class BoardController {
    
	private final BoardService boardService;
	
	/**
	 * 조건에 맞는 게시물 목록 정보를 페이지네이션 정보와 함께 반환
	 * 
	 * @param type 게시판 종류
	 * @param page 게시물 목록 페이지 번호
	 * @param condition 게시물 검색 조건
	 * @param keyword 게시물 검색어
	 * @return 조건에 맞는 게시물 정보
	 */
	@GetMapping
	public ResponseEntity<?> getBoardList(@RequestParam(name = "type") String type,
										  @RequestParam(name = "page", defaultValue = "1") String page,
										  @RequestParam(name = "condition", required = false) String condition,
										  @RequestParam(name = "keyword", required = false) String keyword) {
		
		log.info("type : {}", type);
		
		// 게시판 조회 정보 DTO에 담아서 전달
		BoardListDTO boardListInfo = new BoardListDTO(type, page, condition, keyword, 0, 0);
		
		Map<String, Object> boards = boardService.getBoards(boardListInfo);
		
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.SUCCESS, boards, "게시물 목록 응답 성공"));
	};
}
