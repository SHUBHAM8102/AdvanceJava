package com.capg.springBootBasics;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
	@Id
	public int id;
	public long phone;
	public String email;
	public String name;
	public int age;
	public String gender;
	public String dob;
	
	
	
	
	
	public Customer() {
		
	}
	public Customer(int id, long phone, String email, String name, int age, String gender, String dob) {
		
		this.id = id;
		this.phone = phone;
		this.email = email;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.dob = dob;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", phone=" + phone + ", email=" + email + ", name=" + name + ", age=" + age
				+ ", gender=" + gender + ", dob=" + dob + "]";
	}
	
	
	

}
