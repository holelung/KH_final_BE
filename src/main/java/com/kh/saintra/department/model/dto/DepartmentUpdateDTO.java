package com.kh.saintra.department.model.dto;

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
public class DepartmentUpdateDTO {
	
	@NotBlank
	@Size(min = 2, max = 10, message = "부서 이름이 너무 짧거나 깁니다.")
	private String departmentName;
	
	@NotBlank
	@Pattern(regexp = "^(Y|N)$", message = "허용되지 않은 권한 식별값 입니다.")
	private String personnel;
	
	@NotBlank
	@Pattern(regexp = "^(Y|N)$", message = "허용되지 않은 권한 식별값 입니다.")
	private String inspect;
	
	@NotBlank
	private Long userId;
}
