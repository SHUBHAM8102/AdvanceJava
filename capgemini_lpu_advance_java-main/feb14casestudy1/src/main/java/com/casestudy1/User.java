package com.casestudy1;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	private Long id;
	private String name;
	private String email;
	
	@OneToOne
	private Profile profile;
	
	@OneToMany
	private List<PurchaseOrder> purchaseOrder;
	
	
	
	public void setPurchaseOrder(List<PurchaseOrder> purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public User() {}

	public User(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<PurchaseOrder> getPurchaseOrder() {
		return purchaseOrder;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", profile=" + profile + ", purchaseOrder="
				+ purchaseOrder + "]";
	}
	
	
	
	
	
	
	
	
	
}
