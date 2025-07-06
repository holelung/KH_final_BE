package com.kh.saintra.board.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class BoardVO {

	private Long id;
	private String username;
	private String realname;
	private String title;
	private String content;
	private Date createDate;
}
