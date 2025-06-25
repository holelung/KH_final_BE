package com.kh.saintra.comment.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class CommentListDTO {

	@NotBlank
	@Pattern(regexp = "^(bulletin|free|anonymous|\\d+)$", message = "허용되지 않은 게시판 식별값 입니다.")
	private String type;
	
	@NotBlank
	@Size(min = 1)
	private Long boardId;
	
	@NotBlank
	@Size(min = 1)
	private int page;
	
	private int limit;
	
	private int offset;
}
