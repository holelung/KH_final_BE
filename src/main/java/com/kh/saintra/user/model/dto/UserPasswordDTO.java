package com.kh.saintra.user.model.dto;

import com.kh.saintra.global.annotation.FieldMatch;
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
@FieldMatch(first = "prevPassword", second = "password", message = "이전 비밀번호와 같을 수 없습니다.")
public class UserPasswordDTO {
    
    private Long id;
    
    // 과거 비밀번호
    private String prevPassword;

    // 새로운 비밀번호
    private String password;
}
