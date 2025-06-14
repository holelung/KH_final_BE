package com.kh.saintra.global.validator;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.kh.saintra.global.annotation.FieldMatch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/***
 * 인자 두개의 값이 같은지 확인하는 Validator Class
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        
        try{
            Object firstValue = 
                BeanUtils.getPropertyDescriptor(value.getClass(), firstFieldName)
                    .getReadMethod()
                    .invoke(value);
                    
            Object secondValue =
                BeanUtils.getPropertyDescriptor(value.getClass(), secondFieldName)
                    .getReadMethod()
                    .invoke(value);
                    
            return Objects.equals(firstValue, secondValue);
        } catch (Exception e) {
            return false;
        }
    }
    
}
