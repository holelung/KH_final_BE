package com.kh.saintra.global.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kh.saintra.global.validator.FieldMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = FieldMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {
    String message() default "두 필드의 값이 일치하지 않습니다.";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String first();

    String second();
    
}
