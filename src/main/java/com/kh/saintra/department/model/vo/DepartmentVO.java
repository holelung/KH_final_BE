package com.kh.saintra.department.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class DepartmentVO {

	private Long id;
	private String deptName;
	private String username;
	private String realname;
	private String personnel;
	private String inspect;
	private String isActive;
}
