package com.prac;

public class Employee {
	private int id;
	private String name;
	private int age;
	private String dept;
	
	Employee(int id,String name,int age,String dept){
		this.id=id;
		this.name=name;
		this.age=age;
		this.dept=dept;
	}
	public boolean isValidAge() {
		if(age>17) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean isValidDept() {
		if(dept.length()<3 ) {
			return false;
			
		}
		else {
			return dept.matches("[a-zA-Z]+");
		}
	}

}
