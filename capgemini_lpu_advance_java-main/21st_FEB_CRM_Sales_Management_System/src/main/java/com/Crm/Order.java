package com.Crm;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	private String orderDate;
	private double totalAmount;
	
	@ManyToOne
	private Lead lead;
	
	@ManyToMany()
	@JoinTable(name="Order_product",joinColumns=@JoinColumn(name="order_id"),
	inverseJoinColumns=@JoinColumn(name="product_id"))
	private Set<Product> products;
	
	@OneToOne(mappedBy="order",cascade=CascadeType.ALL)
	private Support_Ticket supportTicket;
	
	



	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Lead getLead() {
		return lead;
	}
	public void setLead(Lead lead) {
		this.lead = lead;
	}
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	public Support_Ticket getSupportTicket() {
		return supportTicket;
	}
	public void setSupportTicket(Support_Ticket supportTicket) {
		this.supportTicket = supportTicket;
	}
	
	

}
