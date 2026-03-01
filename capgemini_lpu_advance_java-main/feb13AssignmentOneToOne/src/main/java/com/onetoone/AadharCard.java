package com.onetoone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "aadhar_card")
public class AadharCard {
	@Id
	private int aadharId;
	private String aadharNumber;
	private String address;
	private String issueDate;
	public AadharCard(int aadharId, String aadharNumber, String address, String issueDate) {
		super();
		this.aadharId = aadharId;
		this.aadharNumber = aadharNumber;
		this.address = address;
		this.issueDate = issueDate;
	}
	
	
	public AadharCard() {}


	public int getAadharId() {
		return aadharId;
	}


	public void setAadharId(int aadharId) {
		this.aadharId = aadharId;
	}


	public String getAadharNumber() {
		return aadharNumber;
	}


	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getIssueDate() {
		return issueDate;
	}


	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	
	
	
	
}
