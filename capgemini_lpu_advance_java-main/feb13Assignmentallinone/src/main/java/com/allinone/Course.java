package com.allinone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class Course {
	
	@Id
	private int id;
	private String title;
	private int credits;
	public Course(int id, String title, int credits) {
		super();
		this.id = id;
		this.title = title;
		this.credits = credits;
	}
	
	
	
	public Course() {}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public int getCredits() {
		return credits;
	}



	public void setCredits(int credits) {
		this.credits = credits;
	}



	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", credits=" + credits + "]";
	}
	
	
	
	
}
