package com.family.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.family.models.FormModel;

@Component
public class CheckParentOlderThanChild implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return FormModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormModel formModel = (FormModel) target;
		if (formModel.getPerson1().getBirthDate().after(formModel.getPerson2().getBirthDate())) {
			if (formModel.getPerson2().getRelations().equals("FATHER")
					|| formModel.getPerson2().getRelations().equals("MOTHER")) {
				System.out.println("parent should be older than children!");
				errors.reject("error 3", "parent " + formModel.getPerson1().getBirthDate()
						+ " should be older than children " + formModel.getPerson2().getBirthDate());
			}

		} else if (formModel.getPerson2().getBirthDate().after(formModel.getPerson1().getBirthDate())) {
			if (formModel.getPerson2().getRelations().equals("SON")
					|| formModel.getPerson2().getRelations().equals("DAUGHTER")) {
				System.out.println("parent should be older than children!");
				errors.reject("error 3", "parent " + formModel.getPerson2().getBirthDate()
						+ " should be older than children " + formModel.getPerson1().getBirthDate());
			}
		}
	}

}
