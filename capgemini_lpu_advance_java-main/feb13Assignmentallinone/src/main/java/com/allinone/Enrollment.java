package com.allinone;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollment")
public class Enrollment {
	
	@Id
	private int id;
	private String semester;
	private String grade;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Course course;
	
	
	
	
	public Course getCourse() {
		return course;
	}


	public void setCourse(Course course) {
		this.course = course;
	}


	public Enrollment(int id, String semester, String grade) {
		super();
		this.id = id;
		this.semester = semester;
		this.grade = grade;
	}
	
	
	public Enrollment() {}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getSemester() {
		return semester;
	}


	public void setSemester(String semester) {
		this.semester = semester;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	@Override
	public String toString() {
		return "Enrollment [id=" + id + ", semester=" + semester + ", grade=" + grade + "]";
	}
	
	
	
	
	
}
