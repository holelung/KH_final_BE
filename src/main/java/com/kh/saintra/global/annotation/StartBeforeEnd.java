package com.kh.saintra.global.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.kh.saintra.global.validator.StartBeforeEndValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = StartBeforeEndValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface StartBeforeEnd {
    
    String message() default "시작 날짜는 종료날짜보다 이전이어야 합니다";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 필드명 설정
    String start();

    String end();

}
