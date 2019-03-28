package com.family.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.family.models.FormModel;

@Component
public class CheckSameNameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FormModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormModel model = (FormModel) target;
		if (model.getPerson1().getName().equals(model.getPerson2().getName())) {
			System.out.println("error occurred");
			errors.reject("error 1", "person can not relate to himself!");
		}
	}

}
