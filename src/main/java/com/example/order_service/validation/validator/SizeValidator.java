package com.example.order_service.validation.validator;

import com.example.order_service.controller.request.SizeRequest;
import com.example.order_service.validation.annotation.SizeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeValidator implements ConstraintValidator<SizeConstraint, SizeRequest> {

    @Override
    public void initialize(SizeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SizeRequest sizeRequest, ConstraintValidatorContext constraintValidatorContext) {
        return (sizeRequest.getSizeNumber() == null || sizeRequest.getSizeCharacter() == null) &&
                (sizeRequest.getSizeNumber() != null || sizeRequest.getSizeCharacter() != null);
    }
}
