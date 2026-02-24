package com.Crm;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	private String productName;
	private double price;
	
	@ManyToMany(mappedBy = "products")
	private Set<Order> orders;

	@ManyToMany(mappedBy = "products")
	private Set<SalesEmployee> employees;
	
	@OneToOne
	@JoinColumn(name="ticket_product")
	private Support_Ticket supportTicket;
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	public Set<SalesEmployee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<SalesEmployee> employees) {
		this.employees = employees;
	}
	public Support_Ticket getSupportTicket() {
		return supportTicket;
	}
	public void setSupportTicket(Support_Ticket supportTicket) {
		this.supportTicket = supportTicket;
	}

}
