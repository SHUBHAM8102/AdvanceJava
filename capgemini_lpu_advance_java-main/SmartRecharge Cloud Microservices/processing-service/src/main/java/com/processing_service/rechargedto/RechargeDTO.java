package com.processing_service.rechargedto;

import java.io.Serializable;

public class RechargeDTO implements Serializable {
	private String mobilenum;
	private double amount;
	private String operator;
	 private String requestId; 


		public RechargeDTO() {

		}
		
	public RechargeDTO(String mobilenum, double amount, String operator, String requestId) {
		super();
		this.mobilenum = mobilenum;
		this.amount = amount;
		this.operator = operator;
		this.requestId = requestId;
	}

	public String getRequestId() {
		return requestId;
	}

	 public void setRequestId(String requestId) {
		 this.requestId = requestId;
	 }


	public String getMobilenum() {
		return mobilenum;
	}

	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
