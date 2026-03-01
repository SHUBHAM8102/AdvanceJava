package com.library.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class LibraryBranch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private long branchId;
	private String name;
	private String location;
	private String contactNumber;
	@OneToMany(mappedBy="branch",cascade=CascadeType.ALL)
	private List<Book> book=new ArrayList<>();
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public List<Book> getBook() {
		return book;
	}
	public void setBook(List<Book> book) {
		this.book = book;
	}
	@Override
	public String toString() {
		return "LibraryBranch [branchId=" + branchId + ", name=" + name + ", location=" + location + ", contactNumber="
				+ contactNumber + ", book=" + book + "]";
	}
	
}
