package com.casestudy1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {
	@Id
	private Long id;
	private String mode;
	private double amount;
	
	@ManyToOne
	private PurchaseOrder purchaseOrder;

	public Payment(Long id, String mode, double amount) {
		super();
		this.id = id;
		this.mode = mode;
		this.amount = amount;
	}
	
	public Payment() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", mode=" + mode + ", amount=" + amount + ", purchaseOrder=" + purchaseOrder + "]";
	}
	
	
	
}
