package com.kh.saintra.board.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

	@NotBlank
	@Pattern(regexp = "^(bulletin|free|anonymous|\\d+)$", message = "허용되지 않은 게시판 식별값 입니다.")
	private String type;
	
	@Positive
	private int page;
	
	@Pattern(regexp = "^(title|content|writer)$", message = "허용되지 않은 검색 조건 입니다.")
	private String condition;
	
	@Size(min = 2, message = "검색어가 너무 짧습니다.")
	private String keyword;
	
	private int limit;
	
	private int offset;
}
