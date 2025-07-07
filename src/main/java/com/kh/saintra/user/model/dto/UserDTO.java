package com.kh.saintra.user.model.dto;



import java.time.LocalDateTime;
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
public class UserDTO {
    
    private Long id;

    @NotBlank(message = "아이디(사번)을 입력해주세요")
    @Pattern(regexp = Regexp.USERNAME, message = "숫자 8-12자리여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = Regexp.PASSWORD, message="비밀번호는 8-20자 이며 영어,숫자,특수문자를 최소 1회사용하여야합니다.")
    private String password;

    @NotBlank(message = "성함을 입력해주세요")
    @Pattern(regexp = Regexp.REAL_NAME, message="이름은 한글로 2-6글자입니다.")
    private String realname;

    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = Regexp.EMAIL, message="이메일처럼 입력해주세요")
    private String email;

    @NotBlank(message = "주소를 입력해주세요")
    private String address1;
    @NotBlank(message = "상세주소를 입력해주세요")
    private String address2;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = Regexp.PHONE, message = "전화번호 형식에 맞지 않습니다.")
    private String phone;

    @NotBlank(message = "주민번호를 입력해주세요")
    @Pattern(regexp = Regexp.SSN, message = "주민번호 형식에 맞지 않습니다.")
    private String ssn;

    private Long jobId;
    private String jobName;
    private Long deptId;
    private String deptName;
    private Long teamId;
    private String teamName;
    private String role;
    private LocalDateTime enrollDate;
    private String isActive;    
    
}
