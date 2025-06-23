package com.kh.saintra.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class LoginFormDTO {
    
    @NotBlank(message = "아이디(사번)을 입력해주세요")
    @Pattern(regexp = "^[0-9]{8,12}$", message = "숫자 8-12자리여야 합니다.")
    private String username;
    
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$",
            message = "비밀번호는 8-20자 이며 영어,숫자,특수문자를 최소 1회사용하여야합니다.")
    private String password;
    
}
