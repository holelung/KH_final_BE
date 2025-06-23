package com.kh.saintra.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.saintra.board.model.dto.BoardDTO;
import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.vo.BoardVO;

public interface BoardService {
	
	Map<String, Object> getBoards(BoardListDTO boardListInfo);
	
	void insertBoard(BoardDTO boardInfo, List<Long> files);
	
	BoardVO getBoardDetail(String type, String id);
}
