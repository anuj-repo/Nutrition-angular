package com.fertilizer.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fertilizer.annotation.validator.ValidUserByUsernameValidator;


@Constraint(validatedBy = { ValidUserByUsernameValidator.class })
@Retention(RUNTIME)
@Target({ METHOD, FIELD, PARAMETER})
public @interface ValidUserByUsername {

	String message() default "{validation.username.ValidUserByUsername}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
