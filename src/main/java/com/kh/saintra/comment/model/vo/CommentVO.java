package com.kh.saintra.comment.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class CommentVO {

	private Long id;
	private Long boardId;
	private String username;
	private String realname;
	private String content;
	private Date createDate;
}
