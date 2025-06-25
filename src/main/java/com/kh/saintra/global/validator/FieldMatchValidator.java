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
            var firstDescriptor = BeanUtils.getPropertyDescriptor(value.getClass(), firstFieldName);
            var secondDescriptor = BeanUtils.getPropertyDescriptor(value.getClass(), secondFieldName);
    
            if (firstDescriptor == null || secondDescriptor == null) {
                return false;
            }
    
            Object firstValue = firstDescriptor.getReadMethod().invoke(value);
            Object secondValue = secondDescriptor.getReadMethod().invoke(value);
            
            return Objects.equals(firstValue, secondValue);
        } catch (Exception e) {
            return false;
        }
    }
    
}
