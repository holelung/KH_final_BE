package com.kh.saintra.user.model.dto;


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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfileDTO {
    
    private Long id;

    @Pattern(regexp = Regexp.REAL_NAME, message = "이름은 한글만 가능합니다.")
    @NotBlank
    private String realname;

    @NotBlank
    @Pattern(regexp = Regexp.PHONE, message="전화번호 양식에 맞지 않습니다.")
    private String phone;

    @NotBlank(message = "주소를 입력해주세요")
    private String address1;

    @NotBlank(message = "상세 주소를 입력해주세요")
    private String address2;

}
