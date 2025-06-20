package com.kh.saintra.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.saintra.board.model.dao.BoardMapper;
import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.vo.BoardVO;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.InvalidValueException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	
	/**
	 * 게시판 종류를 확인하여 존재하는 게시판인지 확인하고 아닐 경우 예외처리하는 메서드
	 * @param type 게시판 종류
	 */
	private void checkBoardType(String type) {

		log.info("type : {}", type);
		
		// 공지사항, 자유, 익명 게시판인지 확인
		if("bulletin".equals(type) || "free".equals(type) || "anonymous".equals(type)) {
			
			return;
		}
		
		// 숫자가 아닌 다른 값이 왔는지 확인
		int id = 0;
		
		try {
			id = Integer.parseInt(type);
			
		} catch (RuntimeException e) {
			throw new InvalidValueException(ResponseCode.SERVER_ERROR, "존재하지 않는 게시판 입니다.");
		}

		// 부서 번호와 일치하고 활성화 상태인 부서가 있는지 확인
		int result = boardMapper.selectDepartmentCountById(id);
		
		if(result == 0) {
			
			throw new InvalidValueException(ResponseCode.SERVER_ERROR, "존재하지 않는 게시판 입니다.");
		}
			
		return;
	}
	
	@Override
	public Map<String, Object> getBoards(BoardListDTO boardListInfo) {
		
		// 게시판 종류 값이 유효한지 확인
		String type = boardListInfo.getType();
		
		checkBoardType(type);
		
		// 게시판에 존재하는 게시물 목록 페이징 처리
		int page = Integer.parseInt(boardListInfo.getPage());
		int totalBoardCount = boardMapper.selectTotalBoardCount(boardListInfo);
		int boardLimit = 10;
		int buttonLimit = 10;
		int maxPage = (int)Math.ceil((double) totalBoardCount / boardLimit);	
		int startButton = (page - 1) / buttonLimit * buttonLimit + 1;
		int endButton = startButton + buttonLimit - 1;
		int offset = (page - 1) * boardLimit;
		
		boardListInfo.setLimit(boardLimit);
		boardListInfo.setOffset(offset);
		
		List<BoardVO> boardList = boardMapper.selectBoardList(boardListInfo);
		
		Map<String, Object> boards = new HashMap<String, Object>();
		
		boards.put("boardList", boardList);
		boards.put("totalBoardCount", totalBoardCount);
		boards.put("page", page);
		boards.put("boardLimit", boardLimit);
		boards.put("buttonLimit", buttonLimit);
		boards.put("maxPage", maxPage);
		boards.put("startButton", startButton);
		boards.put("endButton", endButton);
		
		return boards;
	}
}
