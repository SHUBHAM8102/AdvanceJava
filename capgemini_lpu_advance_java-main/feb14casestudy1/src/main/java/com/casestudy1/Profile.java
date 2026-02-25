package com.casestudy1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {

	@Id
	private Long id;
	private String phone;
	private String address;
	
	public Profile() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Profile(Long id, String phone, String address) {
		super();
		this.id = id;
		this.phone = phone;
		this.address = address;
	}
	@Override
	public String toString() {
		return "Profile [id=" + id + ", phone=" + phone + ", address=" + address + "]";
	}
	
	
	
}
