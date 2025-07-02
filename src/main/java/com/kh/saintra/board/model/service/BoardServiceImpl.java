package com.kh.saintra.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.saintra.board.model.dao.BoardMapper;
import com.kh.saintra.board.model.dto.BoardDeleteDTO;
import com.kh.saintra.board.model.dto.BoardDetailDTO;
import com.kh.saintra.board.model.dto.BoardInsertDTO;
import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.dto.BoardUpdateDTO;
import com.kh.saintra.board.model.vo.BoardVO;
import com.kh.saintra.file.model.dao.FileMapper;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.error.exceptions.EntityNotFoundException;
import com.kh.saintra.global.error.exceptions.InvalidAccessException;
import com.kh.saintra.global.error.exceptions.InvalidValueException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	private final FileMapper fileMapper;
	
	/**
	 * 게시판 종류를 확인하여 존재하는 게시판인지 확인하고 아닐 경우 예외처리하는 메서드
	 * @param type 게시판 종류
	 */
	@Override
	public void checkBoardType(String type) {
		
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
		int page = boardListInfo.getPage();
		int totalBoardCount = boardMapper.selectTotalBoardCount(boardListInfo);
		int boardLimit = 12;
		int buttonLimit = 10;
		int maxPage = (int)Math.ceil((double) totalBoardCount / boardLimit);	
		int startButton = (page - 1) / buttonLimit * buttonLimit + 1;
		int endButton = startButton + buttonLimit - 1;
		int offset = (page - 1) * boardLimit;
		
		boardListInfo.setLimit(boardLimit);
		boardListInfo.setOffset(offset);
		
		log.info("condition: {}", boardListInfo.getCondition());
		
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

	@Override
	public void insertBoard(BoardInsertDTO boardInsertInfo) {
		
		// 게시판 종류 확인
		String type = boardInsertInfo.getType();
		
		checkBoardType(type);
		
		// 작성자 정보 토큰에서 꺼내 DTO에 삽입(나중에 작업)
		
		// 게시물 내용 첨부 파일 관련 편집(나중에 작업)
		
		// 게시물 정보 DB에 삽입
		if(boardMapper.insertBoard(boardInsertInfo) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "게시물 등록에 실패 했습니다.");
		}
		
		// 게시물 첨부 파일 정보 DB에 삽입
		List<Long> files = boardInsertInfo.getFiles();
		
		if(files != null) {
			
			// 게시물 번호 가져오기
			Long boardId = boardMapper.selectLatestBoardIdByConditions(boardInsertInfo);
			
			if(boardMapper.insertBoardFiles(type, boardId, files) != files.size()) {
				
				throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "게시물 첨부 파일 저장에 실패 했습니다.");
			}
		}
	}
	
	@Override
	public Map<String, Object> getBoardDetail(BoardDetailDTO boardDetailInfo) {
		
		// 게시판 종류 확인
		String type = boardDetailInfo.getType();
		
		checkBoardType(type);
		
		// 게시물 정보 가져오기
		BoardVO boardDetail = boardMapper.selectBoardDetail(type, boardDetailInfo.getBoardId());
		
		// 게시물이 존재하는지 검사
		if(boardDetail == null) {
			
			throw new EntityNotFoundException(ResponseCode.SERVER_ERROR, "존재하지 않는 게시물 입니다.");
		}
		
		// 게시물 기존 첨부 파일 정보 가져오기
		Long boardId = boardDetail.getId();
		
		List<Long> files = boardMapper.selectBoardFiles(type, boardId);
		
		Map<String, Object> boardDetailMap = new HashMap<String, Object>();
		
		boardDetailMap.put("boardDetail", boardDetail);
		boardDetailMap.put("files", files);
		
		return boardDetailMap;
	}

	@Override
	public void updateBoard(BoardUpdateDTO boardUpdateInfo) {
		
		// 게시판 종류 확인
		String type = boardUpdateInfo.getType();
		
		checkBoardType(type);
		
		// 작성자 정보 토큰에서 꺼내 DTO에 삽입(나중에 작업)
		Long userId = boardUpdateInfo.getUserId();
		
		// 게시물의 작성자가 맞는지 확인
		Long boardId = boardUpdateInfo.getBoardId();
		
		if(boardMapper.selectBoardCountByUserId(type, boardId, userId) != 1) {
			
			throw new InvalidAccessException(ResponseCode.SERVER_ERROR, "게시물 수정 권한이 없습니다.");
		}
		
		// 게시물 기존 첨부 파일 정보 삭제
		
		List<Long> oldFiles = boardMapper.selectBoardFiles(type, boardId);
		
		if(boardMapper.deleteBoardFiles(type, boardId) != oldFiles.size()) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "게시물 첨부 파일 기존 정보 삭제에 실패 했습니다.");
		}
		
		// 새로운 게시물 첨부 파일 정보 저장
		List<Long> newFiles = boardUpdateInfo.getFiles();
		
		if(boardMapper.insertBoardFiles(type, boardId, newFiles) != newFiles.size()) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "게시물 첨부 파일 새로운 정보 저장에 실패 했습니다.");
		}
		
		// 게시물 내용 첨부 파일 관련 편집(나중에 작업)
		
		// 게시물 DB 업데이트
		if(boardMapper.updateBoard(boardUpdateInfo) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "게시물 수정에 실패 했습니다.");
		}
	}

	@Override
	public void deleteBoard(BoardDeleteDTO boardDeleteInfo) {
		
		// 게시판 종류 확인
		String type = boardDeleteInfo.getType();
		
		checkBoardType(type);
		
		// 작성자 정보 토큰에서 꺼내 DTO에 삽입(나중에 작업)
		Long userId = boardDeleteInfo.getUserId();
		
		// 게시물의 작성자가 맞는지 확인
		Long boardId = boardDeleteInfo.getBoardId();
		
		if(boardMapper.selectBoardCountByUserId(type, boardId, userId) != 1) {
			
			throw new InvalidAccessException(ResponseCode.SERVER_ERROR, "게시물 수정 권한이 없습니다.");
		}
		
		// 게시물 첨부 파일 버킷에서 삭제(나중에 작업)
		List<Long> files = boardDeleteInfo.getFiles();
		
		// 게시물 첨부 파일 정보 삭제 1
		for(Long fileId : files) {
			
			if(fileMapper.deleteFileInfo(fileId) != 1) {
				
				throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "게시물 첨부 파일 정보 삭제에 실패 했습니다.");
			}
		}
		
		// 게시물 첨부 파일 정보 삭제 2
		if(boardMapper.deleteBoardFiles(type, boardId) != files.size()) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "게시물 첨부 파일 정보 삭제에 실패 했습니다.");
		}
		
		// 게시물 삭제
		if (boardMapper.disableBoard(boardDeleteInfo) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "게시물 삭제에 실패 했습니다.");
		}
	}
}
