package com.family.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.family.models.DatabaseModel;
import com.family.models.FormModel;
import com.family.models.Person;
import com.family.validators.CheckParentOlderThanChild;
import com.family.validators.CheckRelationExists;
import com.family.validators.CheckSameNameValidator;
import com.family.validators.CheckUserExists;

@Controller
public class IndexController {

	@Autowired
	private DatabaseModel databaseModel;

	@Autowired
	private CheckUserExists checkUserExistsValidator;

	@Autowired
	private CheckParentOlderThanChild checkParentOlderThanChildValidator;

	@Autowired
	private CheckRelationExists checkRelationExistsValidator;

	@Autowired
	private CheckSameNameValidator checkSameNameValidator;

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@ModelAttribute("relationList")
	public List<String> getRelations() {
		return databaseModel.getRelationList();
	}

	@ModelAttribute("nameList")
	public List<String> getNames() {
		return databaseModel.getNamesList();
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		logger.info("opening index page");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("person1", new Person("Maria Smith", null));
		modelAndView.addObject("person2", new Person("John Doe", null));
		modelAndView.addObject("person3", new Person("Alex Mack", null));
		FormModel model = new FormModel();
		model.setPerson1(new Person("Maria Smith", null));
		model.setPerson2(new Person("John Doe", null));
		modelAndView.addObject("people", model);
		modelAndView.setViewName("index");
		// return new ModelAndView("index", "person", person);
		return modelAndView;
	}

	@RequestMapping(value = "/vis", method = RequestMethod.GET)
	public ModelAndView vis() {
		return new ModelAndView();
	}

	@RequestMapping(value = "/clearDatabase", method = RequestMethod.POST)
	public ModelAndView clearDatabase() {
		logger.info("clearing the database");
		databaseModel.cleanDatabase();
		return new ModelAndView("clearDatabaseSuccess");
	}

	// @InitBinder
	// public void dataBinding(WebDataBinder binder) {
	// binder.addValidators(checkSameNameValidator);
	// }

	@RequestMapping(value = "/insertPerson", method = RequestMethod.POST)
	public ModelAndView insertPerson(@Valid @ModelAttribute("person2") Person person, BindingResult bindingResult) {
		logger.info("insert person controller");
		checkUserExistsValidator.validate(person, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			model.addObject("errors1", bindingResult.getAllErrors().get(0).getDefaultMessage());
			model.addObject("person2", person);
			model.addObject("person1", new Person("Maria Smith", null));
			model.addObject("person3", new Person("Alex Mack", null));
			FormModel fmodel = new FormModel();
			fmodel.setPerson1(new Person("Maria Smith", null));
			fmodel.setPerson2(new Person("John Doe", null));
			model.addObject("people", fmodel);
			return model;
		} else {
			databaseModel.insert(person);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("insertSuccess");
			modelAndView.addObject("person", person);
			return modelAndView;
		}

	}

	@RequestMapping(value = "/getPersonTree", method = RequestMethod.POST)
	public ModelAndView getPersonTree(@ModelAttribute("person1") Person person, BindingResult bindingResult) {
		logger.info("get person tree controller");
		if (bindingResult.hasErrors()) {
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			model.addObject("person2", new Person("John Doe", null));
			model.addObject("person3", new Person("Alex Mack", null));
			model.addObject("person1", person);
			FormModel fmodel = new FormModel();
			fmodel.setPerson1(new Person("Maria Smith", null));
			fmodel.setPerson2(new Person("John Doe", null));
			model.addObject("people", fmodel);
			return model;
		} else {
			int id = databaseModel.getPersonID(person);
			// person =
			// databaseModel.setPersonTreeParents(databaseModel.getPersonByID(id));
			// person = databaseModel.getTree(id, new ArrayList<Integer>());
			List<List<Map<String, Object>>> l = databaseModel.nodesEdges(id, new ArrayList<Integer>(),
					new ArrayList<>(), new ArrayList<>());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("personTree");
			// modelAndView.addObject("person", person);
			modelAndView.addObject("jsonList", databaseModel.toJson(l));
			return modelAndView;
		}
	}

	@RequestMapping(value = "/setRelative", method = RequestMethod.POST)
	public ModelAndView setRelative(@Valid @ModelAttribute("people") FormModel people, BindingResult bindingResult,
			HttpServletRequest request) {
		logger.info("set relative controller");
		String relation = people.getPerson2().getRelations();
		int pID = databaseModel.getPersonID(people.getPerson1());
		Person person = databaseModel.getPersonByID(pID);
		int relativeID = databaseModel.getPersonID(people.getPerson2());
		Person relative = databaseModel.getPersonByID(relativeID);
		relative.setRelations(relation);
		people.setPerson1(person);
		people.setPerson2(relative);
		checkParentOlderThanChildValidator.validate(people, bindingResult);
		checkRelationExistsValidator.validate(people, bindingResult);
		checkSameNameValidator.validate(people, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			model.addObject("errors", bindingResult.getAllErrors().get(0).getDefaultMessage());
			model.addObject("person2", new Person("John Doe", null));
			model.addObject("person3", new Person("Alex Mack", null));
			model.addObject("person1", new Person("Maria Smith", null));
			model.addObject("people", people);
			return model;
		} else {
			int rID = databaseModel.getRelationID(relation);
			databaseModel.insertBothRelations(pID, relativeID, rID, person.getBirthDate(), relative.getBirthDate(),
					person.getGender(), relative.getGender());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("setRelativeSuccess");
			modelAndView.addObject("relative", people);
			return modelAndView;
		}

	}

	@RequestMapping(value = "/deletePerson", method = RequestMethod.POST)
	public ModelAndView deletePerson(@ModelAttribute("person3") Person person, BindingResult bindingResult,
			HttpServletRequest request) {
		logger.info("delete person controller");
		int id = databaseModel.getPersonID(person);
		Person p = databaseModel.getPersonByID(id);
		databaseModel.delete(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("deleteSuccess");
		modelAndView.addObject(p);
		return modelAndView;
		// }
	}

}
