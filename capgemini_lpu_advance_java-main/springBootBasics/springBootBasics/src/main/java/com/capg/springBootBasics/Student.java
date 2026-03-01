package com.capg.springBootBasics;

public class Student {
	public String name;
	public String branch;
	public Student(String name, String branch) {
		
		this.name = name;
		this.branch = branch;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", branch=" + branch + "]";
	}

}
