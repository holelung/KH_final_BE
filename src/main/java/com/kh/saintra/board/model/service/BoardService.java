package com.kh.saintra.board.model.service;

import java.util.Map;

import com.kh.saintra.board.model.dto.BoardDeleteDTO;
import com.kh.saintra.board.model.dto.BoardDetailDTO;
import com.kh.saintra.board.model.dto.BoardInsertDTO;
import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.dto.BoardUpdateDTO;

public interface BoardService {
	
	void checkBoardType(String type);
	
	Map<String, Object> getBoards(BoardListDTO boardListInfo);
	
	Long insertBoard(BoardInsertDTO boardInsertInfo);
	
	Map<String, Object> getBoardDetail(BoardDetailDTO boardDetailInfo);
	
	void updateBoard(BoardUpdateDTO boardUpdateInfo);
	
	void deleteBoard(BoardDeleteDTO boardDeleteInfo);
}
