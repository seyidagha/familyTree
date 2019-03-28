package com.family.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.family.models.DatabaseModel;
import com.family.models.Person;

@Component
public class CheckUserExists implements Validator {

	@Autowired
	private DatabaseModel databaseModel;

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		if (databaseModel.userExists(person)) {
			System.out.println("user exists!");
			errors.reject("error 2", "this person already exists!");
		}
	}

}
