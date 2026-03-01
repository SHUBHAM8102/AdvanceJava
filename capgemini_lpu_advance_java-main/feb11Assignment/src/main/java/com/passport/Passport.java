package com.passport;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "passport")
public class Passport {
	
	@Id
	private String passportno;
	private String name;
	private String gender;
	private String address;
	private int no_of_travellers;
	public String getPassportno() {
		return passportno;
	}
	public void setPassportno(String passportno) {
		this.passportno = passportno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getNo_of_travellers() {
		return no_of_travellers;
	}
	public void setNo_of_travellers(int no_of_travellers) {
		this.no_of_travellers = no_of_travellers;
	}
	public Passport(String passportno, String name, String gender, String address, int no_of_travellers) {
		super();
		this.passportno = passportno;
		this.name = name;
		this.gender = gender;
		this.address = address;
		this.no_of_travellers = no_of_travellers;
	}
	
	
	public Passport() {}
	
	
}
