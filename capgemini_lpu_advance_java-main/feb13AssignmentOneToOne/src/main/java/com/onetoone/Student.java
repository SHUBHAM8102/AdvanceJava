package com.onetoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	@Id
	private int studentId;
	private String name;
	private String email;
	private String branch;
	
	@OneToOne
	private AadharCard aadharCard;
	
	@OneToOne 
	private HostelRoom hostelRoom;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public AadharCard getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(AadharCard aadharCard) {
		this.aadharCard = aadharCard;
	}

	public HostelRoom getHostelRoom() {
		return hostelRoom;
	}

	public void setHostelRoom(HostelRoom hostelRoom) {
		this.hostelRoom = hostelRoom;
	}

	public Student(int studentId, String name, String email, String branch, AadharCard aadharCard,
			HostelRoom hostelRoom) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.email = email;
		this.branch = branch;
		this.aadharCard = aadharCard;
		this.hostelRoom = hostelRoom;
	}
	
	public Student() {}
	
}
