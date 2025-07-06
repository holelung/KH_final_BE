package com.kh.saintra.comment.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.saintra.board.model.dao.BoardMapper;
import com.kh.saintra.board.model.service.BoardService;
import com.kh.saintra.board.model.vo.BoardVO;
import com.kh.saintra.comment.model.dao.CommentMapper;
import com.kh.saintra.comment.model.dto.CommentDeleteDTO;
import com.kh.saintra.comment.model.dto.CommentInsertDTO;
import com.kh.saintra.comment.model.dto.CommentListDTO;
import com.kh.saintra.comment.model.dto.CommentUpdateDTO;
import com.kh.saintra.comment.model.vo.CommentVO;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.error.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final BoardService boardService;
	private final BoardMapper boardMapper;
	private final CommentMapper commentMapper;

	@Override
	public Map<String, Object> getCommentList(CommentListDTO commentListInfo) {
		
		// 게시판 종류 확인
		String type = commentListInfo.getType();
		
		boardService.checkBoardType(type);
		
		// 게시물 확인
		Long boardId = commentListInfo.getBoardId();
		
		BoardVO board = boardMapper.selectBoardDetail(type, boardId);
		
		if(board == null) {
			
			throw new EntityNotFoundException(ResponseCode.SERVER_ERROR, "존재하지 않는 게시물 입니다.");
		}
		
		// 페이지네이션 후, 댓글 목록 가져오기
		int page = commentListInfo.getPage();
		int totalCommentCount = commentMapper.selectTotalCommentCount(type, boardId);
		int commentLimit = 20;
		int buttonLimit = 10;
		int maxPage = (int)Math.ceil((double) totalCommentCount / commentLimit);	
		int startButton = (page - 1) / buttonLimit * buttonLimit + 1;
		int endButton = startButton + buttonLimit - 1;
		int offset = (page - 1) * commentLimit;
		
		commentListInfo.setLimit(buttonLimit);
		commentListInfo.setOffset(offset);
		
		List<CommentVO> commentList = commentMapper.selectCommentList(commentListInfo);
		
		// 반환 맵 생성
		Map<String, Object> commentMap = new HashMap<String, Object>();
		
		commentMap.put("commentList", commentList);
		commentMap.put("totalCommentCount", totalCommentCount);
		commentMap.put("page", page);
		commentMap.put("commentLimit", commentLimit);
		commentMap.put("buttonLimit", buttonLimit);
		commentMap.put("maxPage", maxPage);
		commentMap.put("startButton", startButton);
		commentMap.put("endButton", endButton);
		
		return commentMap;
	}

	@Override
	public void insertComment(CommentInsertDTO commentInsertInfo) {
		
		// 토큰에서 인증 정보 확인
		
		// 게시판 종류 확인
		String type = commentInsertInfo.getType();
		
		boardService.checkBoardType(type);
		
		// 댓글 저장
		if(commentMapper.insertComment(commentInsertInfo) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "댓글 등록에 실패 했습니다.");
		}
	}

	@Override
	public void updateComment(CommentUpdateDTO commentUpdateInfo) {

		// 토큰에서 인증 정보 확인
		
		// 게시판 종류 확인
		String type = commentUpdateInfo.getType();
		
		boardService.checkBoardType(type);
		
		// 댓글 수정
		if(commentMapper.updateComment(commentUpdateInfo) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "댓글 수정에 실패 했습니다.");
		}
	}

	@Override
	public void deleteComment(CommentDeleteDTO commentDeleteInfo) {
		
		// 토큰에서 인증 정보 확인
		
		// 게시판 종류 확인
		String type = commentDeleteInfo.getType();
		
		boardService.checkBoardType(type);
		
		// 댓글 삭제
		if(commentMapper.deleteComment(commentDeleteInfo) != 1) {
			
			throw new DatabaseOperationException(ResponseCode.SERVER_ERROR, "댓글 삭제에 실패 했습니다.");
		}
	}
}
