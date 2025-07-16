package com.kh.saintra.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.saintra.board.model.dto.BoardDeleteDTO;
import com.kh.saintra.board.model.dto.BoardDetailDTO;
import com.kh.saintra.board.model.dto.BoardInsertDTO;
import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.dto.BoardUpdateDTO;
import com.kh.saintra.board.model.vo.BoardVO;

@Mapper
public interface BoardMapper {

	int selectDepartmentCountById(int id);
	
	int selectTotalBoardCount(BoardListDTO boardListInfo);
	
	List<BoardVO> selectBoardList(BoardListDTO boardListInfo);
	
	BoardVO selectBoardDetail(BoardDetailDTO boardDetailInfo);
	
	List<Long> selectBoardFiles(BoardDetailDTO boardDetailInfo);
	
	int insertBoard(BoardInsertDTO boardInsertInfo);
	
	Long selectLatestBoardIdByConditions(BoardInsertDTO boardInsertInfo);
	
	int insertBoardFiles(@Param("type") String type, @Param("boardId") Long boardId, @Param("files") List<Long> files);
	
	int selectBoardCountByUserId(@Param("type") String type, @Param("boardId") Long boardId, @Param("userId") Long userId);
	
	int deleteBoardFiles(String type, Long boardId);
	
	int updateBoard(BoardUpdateDTO boardUpdateInfo);
	
	int disableBoard(BoardDeleteDTO boardDeleteInfo);
}
