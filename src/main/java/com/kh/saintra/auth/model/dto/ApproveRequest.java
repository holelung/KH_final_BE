package com.kh.saintra.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApproveRequest {
    
    private String search;
    private Long rowsPerPage;
    private Long currentPage;
}
