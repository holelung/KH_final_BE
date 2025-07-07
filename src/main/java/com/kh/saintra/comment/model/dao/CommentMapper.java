package com.kh.saintra.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.saintra.comment.model.dto.CommentDeleteDTO;
import com.kh.saintra.comment.model.dto.CommentInsertDTO;
import com.kh.saintra.comment.model.dto.CommentListDTO;
import com.kh.saintra.comment.model.dto.CommentUpdateDTO;
import com.kh.saintra.comment.model.vo.CommentVO;

@Mapper
public interface CommentMapper {

	int selectTotalCommentCount(CommentListDTO commentListInfo);
	
	List<CommentVO> selectCommentList(CommentListDTO commentListInfo);
	
	int insertComment(CommentInsertDTO commentInsertInfo);
	
	int updateComment(CommentUpdateDTO commentUpdateInfo);
	
	int deleteComment(CommentDeleteDTO commentDeleteInfo);
}
