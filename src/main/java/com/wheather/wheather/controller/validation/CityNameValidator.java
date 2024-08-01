package com.wheather.wheather.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;



@Component
public class CityNameValidator implements ConstraintValidator<CityNameConstraint , String> {


    @Override
    public void initialize(CityNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        value = value.replaceAll("[^A-Za-z0-9]", "");

        return !isNumeric(value)&& !isAllBlank(value);
    }



    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

    private boolean isAllBlank(String str) {
        return str == null || str.trim().isEmpty();
    }





}
