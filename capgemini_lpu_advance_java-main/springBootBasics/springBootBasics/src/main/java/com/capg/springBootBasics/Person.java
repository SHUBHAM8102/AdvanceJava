package com.capg.springBootBasics;

import java.util.List;

public class Person {
	public int id;
	public String name;
	
	List<String>iteam;

	public Person(int id, String name, List<String> iteam) {
		
		this.id = id;
		this.name = name;
		this.iteam = iteam;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", iteam=" + iteam + "]";
	}
	

}
