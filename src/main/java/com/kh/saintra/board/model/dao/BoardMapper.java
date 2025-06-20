package com.kh.saintra.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.saintra.board.model.dto.BoardListDTO;
import com.kh.saintra.board.model.vo.BoardVO;

@Mapper
public interface BoardMapper {

	int selectDepartmentCountById(int id);
	
	int selectTotalBoardCount(BoardListDTO boardListInfo);
	
	List<BoardVO> selectBoardList(BoardListDTO boardListInfo);
}
