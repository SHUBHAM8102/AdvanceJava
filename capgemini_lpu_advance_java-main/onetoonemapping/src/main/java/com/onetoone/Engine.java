package com.onetoone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Engine {
	@Id
	private int id;
	private String cc;
	String type;
	String fuelType;
	String mileage;
	
	public Engine() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCc() {
		return cc;
	}
	public void setcc(String cc) {
		this.cc = cc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public Engine(int id, String cc, String type, String fuelType, String mileage) {
		super();
		this.id = id;
		this.cc = cc;
		this.type = type;
		this.fuelType = fuelType;
		this.mileage = mileage;
	}
	
	
	
	
}
