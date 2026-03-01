package com.capg.springBootBasics;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Car {
	@Id
	int id;
	public  String brand;
	public  int price;
	
	public Car() {}
//	public Engine engine;
	public Car(String brand, int price) {
		
		this.brand = brand;
		this.price = price;
//		this.engine = engine;
	}
	@Override
	public String toString() {
		return "Car [brand=" + brand + ", price=" + price + "]";
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	

}
