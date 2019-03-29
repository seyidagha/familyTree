package com.family.test;

import java.sql.Date;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.family.models.DatabaseModel;
import com.family.models.Person;

public class TestPeople {
	private static final Logger logger = LoggerFactory.getLogger(TestPeople.class);

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		// Person me = new Person("yashar", "male");
		Person father = new Person("John Doe", "male");
		Date date = java.sql.Date.valueOf("1800-01-01");
		father.setBirthDate(date);

		logger.info("testing people");
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		DatabaseModel model = (DatabaseModel) context.getBean("databaseModel");
		// System.out.println(model.insert(me));
		// System.out.println(model.insertFather(me, father));
		// System.out.println(model.insertMother(me, mother));
		// System.out.println(model.insertSon(me, son));
		// System.out.println(model.insertDaughter(me, daugher));
		// Person p = model.getPersonTree(1470);
		// p = model.setPersonTreeParents(p);
		// model.getAdjacentRelations(model.getPersonByID(1487));
		// List<Integer> myList = model.dfs(1487);
		// Person p = model.getTree(1487, new ArrayList<Integer>());
		// System.out.println(p);
		// context.close();
		// List<Map<String, Object>> l = new ArrayList<>();
		// Map<String, Object> m = new HashMap<>();
		// m.put("from", 1);
		// m.put("to", 2);
		// m.put("arrow", "to");
		// m.put("label", "son");
		// l.add(m);
		// m = new HashMap<>();
		// m.put("from", 1);
		// m.put("to", 3);
		// m.put("arrow", "to");
		// m.put("label", "daughter");
		// l.add(m);
		// List<List<Map<String, Object>>> l = model.nodesEdges(1487, new
		// ArrayList<Integer>(), new ArrayList<>(),
		// new ArrayList<>());
		// System.out.println(model.userExists(father));
		context.close();
	}

}
