package com.family.models;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.family.validators.NotFuture;

public class Person {

	private int id;
	@Size(min = 3, message = "minimum 3 letters required")
	private String name;
	private List<Person> children;
	private Person father;
	private Person mother;
	private Person sibling;
	private String gender;
	@NotFuture
	private Date birthDate;
	private String relations;

	public Person() {

	}

	public Person(String name, String gender) {
		super();
		this.name = name;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Person> getChildren() {
		return children;
	}

	public void setChildren(List<Person> children) {
		this.children = children;
	}

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Person getSibling() {
		return sibling;
	}

	public void setSibling(Person sibling) {
		this.sibling = sibling;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}

}
