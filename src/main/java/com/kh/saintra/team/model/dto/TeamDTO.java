package com.kh.saintra.team.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDTO {
	
    private Long id;
    private String teamName;
    private Long deptId;
    private String isActive;
}
