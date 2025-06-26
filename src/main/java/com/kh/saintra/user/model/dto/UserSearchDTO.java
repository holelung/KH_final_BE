package com.kh.saintra.user.model.dto;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import com.kh.saintra.user.model.enums.Department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSearchDTO {
    
    private String search;
    private Long deptId;
    private Long jobId;
}
