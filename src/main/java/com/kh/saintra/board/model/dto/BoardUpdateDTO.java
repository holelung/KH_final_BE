package com.kh.saintra.board.model.dto;

import java.util.List;

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
public class BoardUpdateDTO {

	@NotBlank
	@Pattern(regexp = "^(bulletin|free|anonymous|\\d+)$", message = "허용되지 않은 게시판 식별값 입니다.")
	private String type;
	
	@NotBlank
	private Long boardId;
	
	@Size(min = 5, max = 20, message = "게시물 제목의 길이가 너무 짧거나 깁니다.")
	@NotBlank(message = "게시물 제목은 비어있을 수 없습니다.")
	private String title;
	
	@Size(min = 5, max = 2000, message = "게시물 내용의 길이가 너무 짧거나 깁니다.")
	@NotBlank(message = "게시물 내용은 비어있을 수 없습니다.")
	private String content;
	
	@Size(max = 20, message = "게시물에 파일을 20개 이상 첨부할 수 없습니다.")
	List<Long> files;
	
	private Long userId;
}
