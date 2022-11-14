package com.example.order_service.validation.annotation;

import com.example.order_service.validation.validator.SizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SizeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SizeConstraint {

    String message() default "One size can have one and only type of character or number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}