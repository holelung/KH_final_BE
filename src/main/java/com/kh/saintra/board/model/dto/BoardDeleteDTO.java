package com.kh.saintra.board.model.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
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
public class BoardDeleteDTO {

	@NotBlank
	@Pattern(regexp = "^(bulletin|free|anonymous|\\d+)$", message = "허용되지 않은 게시판 식별값 입니다.")
	private String type;
	
	@Min(1)
	private Long boardId;
	
	@Size(max = 20, message = "게시물에 파일을 20개 이상 첨부할 수 없습니다.")
	List<Long> files;
	
	private Long userId;
}
