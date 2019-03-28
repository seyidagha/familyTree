package com.family.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.family.models.DatabaseModel;
import com.family.models.FormModel;

@Component
public class CheckRelationExists implements Validator {

	@Autowired
	private DatabaseModel databaseModel;

	@Override
	public boolean supports(Class<?> clazz) {
		return FormModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormModel formModel = (FormModel) target;
		int id1 = formModel.getPerson1().getId();
		int id2 = formModel.getPerson2().getId();
		int rid = databaseModel.getRelationID(formModel.getPerson2().getRelations());
		if (databaseModel.relationExists(id1, id2, rid)) {
			System.out.println("relation exists!");
			errors.reject("error 4", "this relation already exists!");
		}
	}

}
