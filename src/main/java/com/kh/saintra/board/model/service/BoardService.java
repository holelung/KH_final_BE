package com.kh.saintra.board.model.service;

import java.util.Map;

import com.kh.saintra.board.model.dto.BoardListDTO;

public interface BoardService {
	
	Map<String, Object> getBoards(BoardListDTO boardListInfo);
}
