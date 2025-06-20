package com.kh.saintra.user.model.dto;

import com.kh.saintra.global.annotation.FieldMatch;

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

public class UserDTO {
    
    private Long id;
    private String name;    
    
}
