package com.kh.saintra.department.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class DepartmentPageVO {

	private Long deptId;
	private String deptName;
	//private List<User> userList;
	//private List<TeamVO> teamList;
}
