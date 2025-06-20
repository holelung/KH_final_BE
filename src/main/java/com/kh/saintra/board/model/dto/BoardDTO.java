package com.kh.saintra.board.model.dto;

import jakarta.validation.constraints.NotBlank;
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
public class BoardDTO {

	@Size(min = 5, max = 20, message = "게시물 제목의 길이가 너무 짧거나 깁니다.")
	@NotBlank(message = "게시물 제목은 비어있을 수 없습니다.")
	private String title;
	
	@NotBlank(message = "게시물 내용은 비어있을 수 없습니다.")
	private String content;
}
