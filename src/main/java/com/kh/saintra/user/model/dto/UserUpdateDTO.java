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
public class UserUpdateDTO {

    private String username;
    private String userRole;
    private Department department;
    private Job job;

}
