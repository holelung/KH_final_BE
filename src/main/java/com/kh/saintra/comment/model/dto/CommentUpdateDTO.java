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
public class CommentUpdateDTO {

	@NotBlank
	@Pattern(regexp = "^(bulletin|free|anonymous|\\d+)$", message = "허용되지 않은 게시판 식별값 입니다.")
	private String type;
	
	@NotBlank
	private Long boardId;
	
	@NotBlank
	private Long commentId;
	
	@NotBlank
	private Long userId;
	
	@NotBlank
	@Size(min = 2, message = "댓글 길이가 너무 짧습니다.")
	private String content;
}
