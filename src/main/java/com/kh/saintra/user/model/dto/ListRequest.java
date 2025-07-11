package com.kh.saintra.user.model.dto;

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
public class ListRequest {
    
    private String search;
    private Long rowsPerPage;
    private Long currentPage;
}
