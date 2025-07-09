package com.kh.saintra.global.validator;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.kh.saintra.global.annotation.StartBeforeEnd;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, Object>{

    private String startField;
    private String endField;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 원하는 형식에 맞게

    public void initialize(StartBeforeEnd constraintAnnotation) {
        this.startField = constraintAnnotation.start();
        this.endField = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            PropertyDescriptor startDesc = new PropertyDescriptor(startField, value.getClass());
            PropertyDescriptor endDesc = new PropertyDescriptor(endField, value.getClass());

            Method startGetter = startDesc.getReadMethod();
            Method endGetter = endDesc.getReadMethod();

            Object startObj = startGetter.invoke(value);
            Object endObj = endGetter.invoke(value);

            if (startObj == null || endObj == null) {
                return true; // null은 @NotNull
            }

            if (!(startObj instanceof String) || !(endObj instanceof String)) {
                return false;
            }

            LocalDateTime startDate = LocalDateTime.parse((String) startObj, formatter);
            LocalDateTime endDate = LocalDateTime.parse((String) endObj, formatter);

            return !startDate.isAfter(endDate);

        } catch (DateTimeParseException e) {
            return false;
        } catch (Exception e) {
            // throw new InvalidValueException(ResponseCode.INVALID_VALUE, "시작날짜는 종료날짜보다 이전시간이어야 합니다.");    
            return false;
        }
        
    }
    
}
