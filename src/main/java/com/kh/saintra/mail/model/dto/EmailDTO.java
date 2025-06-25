package com.kh.saintra.mail.model.dto;

import com.kh.saintra.global.util.Regexp;
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
public class EmailDTO {
    
    @NotBlank
    @Pattern(regexp = Regexp.EMAIL, message="올바른 이메일 형식이 아닙니다.")
    private String email;
    
    @Pattern(regexp = Regexp.VERIFY_CODE, message="올바른 인증 코드 형식이 아닙니다.")
    private String verifyCode;
}
