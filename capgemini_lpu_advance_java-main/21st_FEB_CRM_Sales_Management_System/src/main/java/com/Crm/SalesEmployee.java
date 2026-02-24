package com.Crm;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class SalesEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long empId;
	private String name;
	private String department;
	
	@OneToMany(mappedBy="salesEmployee")
	private List<Lead> lead;
	
	@ManyToMany
	@JoinTable(name="employee_product",
			joinColumns=@JoinColumn(name="employee_id"),inverseJoinColumns=@JoinColumn(name="product_name"))
	private Set<Product> products;
	
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address;
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public List<Lead> getLead() {
		return lead;
	}
	public void setLead(List<Lead> lead) {
		this.lead = lead;
	}
	public Set<Product> getProduct() {
		return products;
	}
	public void setProduct(Set<Product> product) {
		this.products = product;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	

}
