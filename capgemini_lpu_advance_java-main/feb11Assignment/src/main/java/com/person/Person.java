package com.person;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {
	
	@Id
	private int id;
	private String name;
	private String dob;
	private String address;
	private String pincode;
	
	public Person() {}
	
	public Person(int id, String name, String dob, String address, String pincode) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.pincode = pincode;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", dob=" + dob + ", address=" + address + ", pincode=" + pincode
				+ "]";
	}
	
}
