package com.kh.saintra.department.model.dto;

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
public class DepartmentListDTO {
	
	@Pattern(regexp = "^(title|content|writer)$", message = "허용되지 않은 검색 조건 입니다.")
	private String condition;
	
	@Size(min = 2, message = "검색어가 너무 짧습니다.")
	private String keyword;
}
