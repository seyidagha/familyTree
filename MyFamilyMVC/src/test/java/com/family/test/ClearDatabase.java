package com.family.test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.family.models.DatabaseModel;

public class ClearDatabase {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		DatabaseModel model = (DatabaseModel) context.getBean("databaseModel");
		model.cleanDatabase();
		context.close();
	}

}
