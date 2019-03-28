package com.family.validators;

import java.sql.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckFutureBirthdateValidator implements ConstraintValidator<NotFuture, Date> {

	// private String myString;
	//
	// @Override
	// public void initialize(OlderThan constraintAnnotation) {
	// this.myString = constraintAnnotation.myCheck();
	// }

	@Override
	public boolean isValid(Date date, ConstraintValidatorContext arg1) {
		if (date.after(new java.util.Date())) {
			return false;
		} else
			return true;
	}

}
