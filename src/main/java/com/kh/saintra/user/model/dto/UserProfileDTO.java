package com.kh.saintra.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfileDTO {
    
    private String userId;
    private String realname;
    private String phone;
    private String address1;
    private String address2;

}
