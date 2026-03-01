package com.capg.springBootBasics;

import java.util.List;

public class College {
	public String name;
	public String loc;
	List<Student> student;
	public College(String name, String loc, List<Student> student) {
		
		this.name = name;
		this.loc = loc;
		this.student = student;
	}
	@Override
	public String toString() {
		return "College [name=" + name + ", loc=" + loc + ", student=" + student + "]";
	}

}
