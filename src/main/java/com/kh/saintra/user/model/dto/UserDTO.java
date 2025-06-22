package com.kh.saintra.user.model.dto;



import java.time.LocalDateTime;

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
public class UserDTO {
    
    private Long id;

    @NotBlank(message = "아이디(사번)을 입력해주세요")
    @Pattern(regexp = "^[0-9]{8,12}$", message = "숫자 8-12자리여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$", message="비밀번호는 8-20자 이며 영어,숫자,특수문자를 최소 1회사용하여야합니다.")
    private String password;

    @NotBlank(message = "성함을 입력해주세요")
    @Pattern(regexp = "/^[가-힣]{2,6}$/\n", message="이름은 한글로 2-6글자입니다.")
    private String realname;

    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message="이메일처럼 입력해주세요")
    private String email;

    @NotBlank(message = "주소를 입력해주세요")
    private String address1;
    @NotBlank(message = "상세주소를 입력해주세요")
    private String address2;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "/^010-\\d{4}-\\d{4}$/", message = "전화번호 형식에 맞지 않습니다.")
    private String phone;

    @NotBlank(message = "주민번호를 입력해주세요")
    @Pattern(regexp = "/^\\d{6}-[1-4]\\d{6}$/\n", message = "주민번호 형식에 맞지 않습니다.")
    private String ssn;

    private String jobId;
    private String deptId;
    private String teamId;
    private String role;
    private LocalDateTime enrollDate;
    private String isActive;    
    
}
