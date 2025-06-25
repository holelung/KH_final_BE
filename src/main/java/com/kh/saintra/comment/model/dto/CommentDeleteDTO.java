package com.kh.saintra.comment.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class CommentDeleteDTO {

	@NotBlank
	@Pattern(regexp = "^(bulletin|free|anonymous|\\d+)$", message = "허용되지 않은 게시판 식별값 입니다.")
	private String type;
	
	@NotBlank
	private Long boardId;
	
	@NotBlank
	private Long commentId;
	
	@NotBlank
	private Long userId;
}
