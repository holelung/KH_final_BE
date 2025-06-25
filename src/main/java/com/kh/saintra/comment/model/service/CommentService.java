package com.kh.saintra.comment.model.service;

import java.util.Map;

import com.kh.saintra.comment.model.dto.CommentDeleteDTO;
import com.kh.saintra.comment.model.dto.CommentInsertDTO;
import com.kh.saintra.comment.model.dto.CommentListDTO;
import com.kh.saintra.comment.model.dto.CommentUpdateDTO;

public interface CommentService {

	Map<String, Object> getCommentList(CommentListDTO commentListInfo);
	
	void insertComment(CommentInsertDTO commentInsertInfo);
	
	void updateComment(CommentUpdateDTO commentUpdateInfo);
	
	void deleteComment(CommentDeleteDTO commentDeleteInfo);
}
