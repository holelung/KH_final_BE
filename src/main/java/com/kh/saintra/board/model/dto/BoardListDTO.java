package com.kh.saintra.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardListDTO {

	private String type;
	private String page;
	private String condition;
	private String keyword;
	private int limit;
	private int offset;
}
