package com.it355.backend.security.annotations;

import com.it355.backend.security.MaxFileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({FIELD, METHOD, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MaxFileSizeValidator.class)
public @interface MaxFileSize {

    String message() default "File size exceeds the maximum allowed size of {value} bytes.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long value();
}