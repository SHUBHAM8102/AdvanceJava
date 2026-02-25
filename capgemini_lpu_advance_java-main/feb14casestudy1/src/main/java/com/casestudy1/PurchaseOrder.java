package com.casestudy1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
	@Id
	private Long id;
	private String orderDate;
	private double amount;
	
	
	public PurchaseOrder() {}
	
	public PurchaseOrder(Long id, String orderDate, double amount) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PurchaseOrder [id=" + id + ", orderDate=" + orderDate + ", amount=" + amount + "]";
	}
	
	
	
	

}
