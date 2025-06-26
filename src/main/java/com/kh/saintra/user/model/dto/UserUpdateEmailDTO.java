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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpdateEmailDTO {

    @Pattern(regexp = Regexp.EMAIL)
    @NotBlank
    private String email;
}
