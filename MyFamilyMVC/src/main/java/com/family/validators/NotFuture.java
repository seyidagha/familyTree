package com.family.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckFutureBirthdateValidator.class)
@Target({ ElementType.FIELD })
public @interface NotFuture {
	String message() default "birthdate can not be in future!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	// String myCheck();
}
