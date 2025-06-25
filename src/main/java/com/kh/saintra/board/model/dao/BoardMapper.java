package com.kh.saintra.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.saintra.board.model.dto.BoardDeleteDTO;
import com.kh.saintra.board.model.dto.BoardInsertDTO;
import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.dto.BoardUpdateDTO;
import com.kh.saintra.board.model.vo.BoardVO;

@Mapper
public interface BoardMapper {

	int selectDepartmentCountById(int id);
	
	int selectTotalBoardCount(BoardListDTO boardListInfo);
	
	List<BoardVO> selectBoardList(BoardListDTO boardListInfo);
	
	BoardVO selectBoardDetail(String type, Long boardId);
	
	List<Long> selectBoardFiles(String type, Long boardId);
	
	int insertBoard(BoardInsertDTO boardInsertInfo);
	
	Long selectLatestBoardIdByConditions(BoardInsertDTO boardInsertInfo);
	
	int insertBoardFiles(String type, Long boardId, List<Long> files);
	
	int selectBoardCountByUserId(String type, Long boardId, Long userId);
	
	int deleteBoardFiles(String type, Long boardId);
	
	int updateBoard(BoardUpdateDTO boardUpdateInfo);
	
	int disableBoard(BoardDeleteDTO boardDeleteInfo);
}
