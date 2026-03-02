package com.onetoone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Car {
	
	@OneToOne	//this will create a column with primary key of engine
	private Engine engine;
	
	@Id
	private int id;
	private String brand;
	private String model;
	private double price;
	private String modelYear;
	
	
	
	public Engine getEngine() {
		return engine;
	}
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	
	public Car(int id, String brand, String model, double price, String modelYear) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.modelYear = modelYear;
	}
	
	public Car() {}
	@Override
	public String toString() {
		return "Car [engine=" + engine + ", id=" + id + ", brand=" + brand + ", model=" + model + ", price=" + price
				+ ", modelYear=" + modelYear + "]";
	}
	
	
	
}
