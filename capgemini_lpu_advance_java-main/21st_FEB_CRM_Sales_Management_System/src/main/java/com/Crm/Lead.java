package com.Crm;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Lead {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long leadId;
	private String source;
	private String status;
	private String name;
	 private String contactInfo;
	
	 public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	@OneToOne
	    @JoinColumn(name = "customer_id")
	    private Customer customer;
	 
	 @ManyToOne
	 @JoinColumn(name="employee_id")
	 private SalesEmployee salesEmployee;
	 
	 @OneToMany(mappedBy="lead",cascade=CascadeType.ALL)
	 private List<Order>order= new ArrayList<>();;
	 
	 
	public long getLeadId() {
		return leadId;
	}
	public void setLeadId(long leadId) {
		this.leadId = leadId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public SalesEmployee getSalesEmployee() {
		return salesEmployee;
	}
	public void setSalesEmployee(SalesEmployee salesEmployee) {
		this.salesEmployee = salesEmployee;
	}
	public List<Order> getOrder() {
		return order;
	}
	public void setOrder(List<Order> order) {
		this.order = order;
	}
	
	
	
	

}
