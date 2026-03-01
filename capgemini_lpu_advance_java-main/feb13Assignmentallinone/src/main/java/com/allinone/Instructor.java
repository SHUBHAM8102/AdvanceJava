package com.allinone;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructor")
public class Instructor {

	@Id
	private int id;
	private String name;
	private String department;

	@OneToOne(cascade = CascadeType.ALL) // hibernate will only save the parent if we want to save this then cascade
	private InstructorProfile instructorProfile;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Course> course;
	
	

	public InstructorProfile getInstructorProfile() {
		return instructorProfile;
	}

	public void setInstructorProfile(InstructorProfile instructorProfile) {
		this.instructorProfile = instructorProfile;
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	public Instructor(int id, String name, String department) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
	}

	public Instructor() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Instructor [id=" + id + ", name=" + name + ", department=" + department + "]";
	}

}
