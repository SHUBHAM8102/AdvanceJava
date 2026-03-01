package com.allinone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructor_profile")
public class InstructorProfile {
	
	@Id
	private int id;
	private String officeRoom;
	private String phone;
	public InstructorProfile(int id, String officeRoom, String phone) {
		super();
		this.id = id;
		this.officeRoom = officeRoom;
		this.phone = phone;
	}
	
	
	public InstructorProfile() {}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getOfficeRoom() {
		return officeRoom;
	}


	public void setOfficeRoom(String officeRoom) {
		this.officeRoom = officeRoom;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	@Override
	public String toString() {
		return "InstructorProfile [id=" + id + ", officeRoom=" + officeRoom + ", phone=" + phone + "]";
	}
	
	
	
}
